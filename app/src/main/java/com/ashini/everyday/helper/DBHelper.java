package com.ashini.everyday.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table tb_task("
                + "task_id integer primary key autoincrement,"
                + "status integer,"
                + "task_description text)");
        sqLiteDatabase.execSQL("create table tb_task_detail("
                + "task_detail_id integer primary key autoincrement,"
                + "task_id integer,"
                + "date text,"
                + "task_description text,"
                + "level integer)");
        sqLiteDatabase.execSQL("create table tb_day("
                + "day_id integer primary key autoincrement,"
                + "date text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
