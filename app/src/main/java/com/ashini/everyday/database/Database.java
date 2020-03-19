package com.ashini.everyday.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ashini.everyday.helper.DBHelper;

public class Database {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDatabase getSqLiteDatabase(Context context) {
        dbHelper = new DBHelper(context, "db_task", null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return sqLiteDatabase;
    }
}
