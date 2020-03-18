package com.ashini.everyday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ashini.everyday.adapter.TaskItemAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] data = {"香蕉","苹果","水蜜桃","橙子","橘子","柚子"};
        RecyclerView recyclerView = findViewById(R.id.task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskItemAdapter(data));
    }
}
