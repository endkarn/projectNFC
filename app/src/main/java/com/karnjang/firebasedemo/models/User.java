package com.karnjang.firebasedemo.models;

/**
 * Created by ssppy on 14-Sep-17.
 */

public class User {
    public String username;
    public int totalXp;
    public String password;
    public int totalPoints;

    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
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

    public String getUserLevel(){
        String userLevel = Integer.toString(totalXp/100);
        return userLevel;
    }
}
