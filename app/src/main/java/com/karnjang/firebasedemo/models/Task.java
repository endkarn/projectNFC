package com.karnjang.firebasedemo.models;

/**
 * Created by ssppy on 19-Jan-18.
 */

public class Task {
    private int taskConditionForCompleteTask;
    private String taskDetail;
    private int taskExpReward;
    private String taskName;
    private int taskPointReward;


    public Task() {

    }


    public int getTaskConditionForCompleteTask() {
        return taskConditionForCompleteTask;
    }

    public void setTaskConditionForCompleteTask(int taskConditionForCompleteTask) {
        this.taskConditionForCompleteTask = taskConditionForCompleteTask;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public int getTaskExpReward() {
        return taskExpReward;
    }

    public void setTaskExpReward(int taskExpReward) {
        this.taskExpReward = taskExpReward;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskPointReward() {
        return taskPointReward;
    }

    public void setTaskPointReward(int taskPointReward) {
        this.taskPointReward = taskPointReward;
    }

}
