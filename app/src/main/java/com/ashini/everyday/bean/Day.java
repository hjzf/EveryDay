package com.ashini.everyday.bean;

public class Day {
    private Integer dayId;
    private String date;

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Day{" +
                "dayId=" + dayId +
                ", date='" + date + '\'' +
                '}';
    }
}
