package com.ashini.everyday;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ashini.everyday.database.Database;
import com.ashini.everyday.helper.DateHelper;

import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        db = new Database().getSqLiteDatabase(CreateTaskActivity.this);
        Button create = findViewById(R.id.buttonCreate);
        Button cancel = findViewById(R.id.buttonCancel);
        final EditText editText = findViewById(R.id.editText);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                String value = editText.getText().toString();
                contentValues.put("task_description", value);
                contentValues.put("status", 0);
                db.insert("tb_task", null, contentValues);
                int maxId = -1;
                Cursor cursor = db.query("tb_task", new String[]{"task_id"}, null, null, null, null, null);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    int anInt = cursor.getInt(0);
                    if (anInt > maxId) {
                        maxId = anInt;
                    }
                }
                cursor.close();
                ContentValues val = new ContentValues();
                val.put("task_id", maxId);
                val.put("task_description", value);
                val.put("date", DateHelper.format(new Date()));
                val.put("level", 0);
                db.insert("tb_task_detail", null, val);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
