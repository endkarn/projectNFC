package com.karnjang.firebasedemo.models;

public class UserAct {
    private String actionResult;
    private String actionStore;
    private String actionDetail;
    private String actionTimeStamp;

    public UserAct(){

    }

//    public UserAct(String actionResult, String actionStore, String actionDetail, String actionTimeStamp) {
//        this.actionResult = actionResult;
//        this.actionStore = actionStore;
//        this.actionDetail = actionDetail;
//        this.actionTimeStamp = actionTimeStamp;
//    }


    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public String getActionStore() {
        return actionStore;
    }

    public void setActionStore(String actionStore) {
        this.actionStore = actionStore;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String getActionTimeStamp() {
        return actionTimeStamp;
    }

    public void setActionTimeStamp(String actionTimeStamp) {
        this.actionTimeStamp = actionTimeStamp;
    }
}
