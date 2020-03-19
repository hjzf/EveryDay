package com.ashini.everyday.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
    public static String format(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return sdf.format(date);
    }
}
