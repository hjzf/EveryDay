package com.ashini.everyday.bean;

import java.util.Date;

public class TaskDetail {
    private Integer taskDetailId;
    private Integer taskId;
    private String taskDescription;
    private String date;
    private Integer level;

    public Integer getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(Integer taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "TaskDetail{" +
                "taskDetailId=" + taskDetailId +
                ", taskId=" + taskId +
                ", taskDescription='" + taskDescription + '\'' +
                ", date='" + date + '\'' +
                ", level=" + level +
                '}';
    }
}
