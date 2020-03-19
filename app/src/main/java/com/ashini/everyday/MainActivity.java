package com.ashini.everyday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashini.everyday.adapter.TaskItemAdapter;
import com.ashini.everyday.bean.TaskDetail;
import com.ashini.everyday.database.Database;
import com.ashini.everyday.helper.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private RecyclerView recyclerView;
    private TextView textView;

    private  List<TaskDetail> taskDetailList  = new ArrayList<>();//数据集
    private TaskItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        db = new Database().getSqLiteDatabase(MainActivity.this);
        textView = findViewById(R.id.create);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateTaskActivity.class));
            }
        });
        recyclerView = findViewById(R.id.task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//设置为线性
        adapter = new TaskItemAdapter(taskDetailList, MainActivity.this);
        recyclerView.setAdapter(adapter);
        refreshRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                refreshRecyclerView();
               Log.e("ashinigit",taskDetailList.toString());
            }
        });
    }

    private void refreshRecyclerView() {
        boolean isExist = false;
        String today = DateHelper.format(new Date());
        Cursor cursor = db.query("tb_day", new String[]{"date"}, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (today.equals(cursor.getString(0))) {
                isExist = true;
            }
        }
        cursor.close();
        if (!isExist) {
            ContentValues values = new ContentValues();
            values.put("status", 0);
            db.update("tb_task", values, null, null);
            List<TaskDetail> taskDetails = new ArrayList<>();
            cursor = db.query("tb_task", new String[]{"task_id", "task_description"}, null, null, null, null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                TaskDetail taskDetail = new TaskDetail();
                taskDetail.setTaskId(cursor.getInt(0));
                taskDetail.setTaskDescription(cursor.getString(1));
                taskDetail.setDate(today);
                taskDetail.setLevel(0);
                taskDetails.add(taskDetail);
            }
            cursor.close();
            for (TaskDetail taskDetail : taskDetails) {
                ContentValues v = new ContentValues();
                v.put("task_id", taskDetail.getTaskId());
                v.put("task_description", taskDetail.getTaskDescription());
                v.put("date", taskDetail.getDate());
                v.put("level", taskDetail.getLevel());
                db.insert("tb_task_detail", null, v);
            }
            ContentValues val = new ContentValues();
            val.put("date", today);
            db.insert("tb_day", null, val);
        }
        taskDetailList.clear();
        cursor = db.query("tb_task_detail", null, "date=?", new String[]{today}, null, null, "task_id");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            TaskDetail taskDetail = new TaskDetail();
            taskDetail.setTaskDetailId(cursor.getInt(0));
            taskDetail.setTaskId(cursor.getInt(1));
            taskDetail.setDate(cursor.getString(2));
            taskDetail.setTaskDescription(cursor.getString(3));
            taskDetail.setLevel(cursor.getInt(4));
            taskDetailList.add(taskDetail);
        }
        cursor.close();
        adapter.notifyDataSetChanged();//刷新全部可见的item
        Log.e("ashini","refresh");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshRecyclerView();
    }
}
