package com.ashini.everyday.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashini.everyday.R;
import com.ashini.everyday.bean.TaskDetail;
import com.ashini.everyday.database.Database;
import com.ashini.everyday.view.CycleProcessBar;

import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {
    private List<TaskDetail> taskDetails;
    private Context context;

    public TaskItemAdapter(List<TaskDetail> taskDetails,Context context) {
        super();
        this.taskDetails = taskDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull TaskItemAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(taskDetails.get(position).getTaskDescription());
        final CycleProcessBar cycleProcessBar = holder.cycleProcessBar;
        cycleProcessBar.setCurrentProcess(taskDetails.get(position).getLevel());
        cycleProcessBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cycleProcessBar.start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        cycleProcessBar.stop();
                        SQLiteDatabase db = new Database().getSqLiteDatabase(context);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("level",(int)cycleProcessBar.getCurrentProcess());
                        db.update("tb_task_detail",contentValues,
                                "task_detail_id=?",
                                new String[]{taskDetails.get(position).getTaskDetailId().toString()});
                        ContentValues values = new ContentValues();
                        values.put("status",1);
                        db.update("tb_task",values,
                                "task_id=?",
                                new String[]{taskDetails.get(position).getTaskId().toString()});
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskDetails.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CycleProcessBar cycleProcessBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.task_item_text);
            cycleProcessBar = itemView.findViewById(R.id.task_item_cycle_process_bar);
            cycleProcessBar.setMaxProcess(100f);
            cycleProcessBar.setSpeed(0.034f);
        }
    }
}
