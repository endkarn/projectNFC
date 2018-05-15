package com.karnjang.firebasedemo.models;

import android.content.Context;
import android.content.SharedPreferences;

public class NewUser {
    public String username;
    public int totalXp;
    public String userid;
    public int totalPoints;
    public String pictureProfile;


    public NewUser() {

    }

//    public User(String username, int totalXp, String password, int totalPoints) {
//        this.username = username;
//        this.totalXp = totalXp;
//        this.userid = password;
//        this.totalPoints = totalPoints;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public NewUser(String username, String userid) {
        this.username = username;
        this.userid = userid;
        this.setTotalXp(10);
        this.setTotalPoints(1000);
        this.setPictureProfile("https://graph.facebook.com/"+userid+"/picture?width=250&height=250");
    }

    public int getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPictureProfile() {
        return pictureProfile;
    }

    public void setPictureProfile(String pictureProfile) {
        this.pictureProfile = pictureProfile;
    }


}
