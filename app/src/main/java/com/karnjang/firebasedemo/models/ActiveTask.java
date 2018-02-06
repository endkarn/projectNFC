package com.karnjang.firebasedemo.models;

public class ActiveTask {
    public int currentCondition;
    public String storeId;
    public int taskStatus;

    public ActiveTask(){

    }

    public int getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(int currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
}
