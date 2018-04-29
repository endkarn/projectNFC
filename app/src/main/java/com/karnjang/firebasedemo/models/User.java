package com.karnjang.firebasedemo.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by ssppy on 14-Sep-17.
 */

public class User {
    public String username;
    public int totalXp;
    public String userid;
    public int totalPoints;
    public String pictureProfile;


    public User() {

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

    public User(String username, String userid) {
        this.username = username;
        this.userid = userid;
        this.setTotalXp(0);
        this.setTotalPoints(0);
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

    public String getUserLevel() {
        Double userLevelD = (0.4 * Math.sqrt(totalXp));
        Integer userLevelI = userLevelD.intValue();
        String userLevel = Integer.toString(userLevelI);
        return userLevel;
    }

    public String WTFjustaPersent() {
        Double userLevelD = (0.4 * Math.sqrt(totalXp));
        String userLevelPer = String.valueOf(userLevelD);
        Log.i("check user","user percent >> userLevelPer = "+ userLevelPer);
        userLevelPer = userLevelPer.substring(userLevelPer.indexOf(".") + 1, userLevelPer.indexOf(".") + 3);
        Log.i("check user","user percent >> userLevelPer (Cooked) = "+ userLevelPer);
        return userLevelPer+"%";
    }

    public String getDefUser(Context context) {
        SharedPreferences userPref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userName = userPref.getString("SH_USERNAME", "");
        return userName;

    }
}
