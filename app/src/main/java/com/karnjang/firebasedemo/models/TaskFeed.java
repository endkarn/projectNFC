package com.karnjang.firebasedemo.models;

public class TaskFeed {
    private String feedUsername;
    private String feedStore;
    private String feedDetail;
    private String feedTimeStamp;

    public TaskFeed() {
    }

    public String getFeedUsername() {
        return feedUsername;
    }

    public void setFeedUsername(String feedUsername) {
        this.feedUsername = feedUsername;
    }

    public String getFeedStore() {
        return feedStore;
    }

    public void setFeedStore(String feedStore) {
        this.feedStore = feedStore;
    }

    public String getFeedDetail() {
        return feedDetail;
    }

    public void setFeedDetail(String feedDetail) {
        this.feedDetail = feedDetail;
    }

    public String getFeedTimeStamp() {
        return feedTimeStamp;
    }

    public void setFeedTimeStamp(String feedTimeStamp) {
        this.feedTimeStamp = feedTimeStamp;
    }
}
