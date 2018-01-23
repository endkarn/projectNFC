package com.karnjang.firebasedemo.models;

/**
 * Created by ssppy on 19-Jan-18.
 */

public class Task {
    private int taskType;
    private String taskName;
    private String taskDetail;
    private int taskCurrentProgress;
    private int taskCompletedProgress;
    private int taskXpReward;
    private int taskPtReward;


    public Task(int taskType, String taskName, String taskDetail, int taskCurrentProgress, int taskCompletedProgress, int taskXpReward, int taskPtReward) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.taskDetail = taskDetail;
        this.taskCurrentProgress = taskCurrentProgress;
        this.taskCompletedProgress = taskCompletedProgress;
        this.taskXpReward = taskXpReward;
        this.taskPtReward = taskPtReward;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    public int getTaskCurrentProgress() {
        return taskCurrentProgress;
    }

    public void setTaskCurrentProgress(int taskCurrentProgress) {
        this.taskCurrentProgress = taskCurrentProgress;
    }

    public int getTaskCompletedProgress() {
        return taskCompletedProgress;
    }

    public void setTaskCompletedProgress(int taskCompletedProgress) {
        this.taskCompletedProgress = taskCompletedProgress;
    }

    public int getTaskXpReward() {
        return taskXpReward;
    }

    public void setTaskXpReward(int taskXpReward) {
        this.taskXpReward = taskXpReward;
    }

    public int getTaskPtReward() {
        return taskPtReward;
    }

    public void setTaskPtReward(int taskPtReward) {
        this.taskPtReward = taskPtReward;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }
}
