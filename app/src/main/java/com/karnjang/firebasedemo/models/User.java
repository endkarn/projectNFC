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
    public String password;
    public int totalPoints;



    public User(){

    }

//    public User(String username, int totalXp, String password, int totalPoints) {
//        this.username = username;
//        this.totalXp = totalXp;
//        this.password = password;
//        this.totalPoints = totalPoints;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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
        Double userLevelD = ( 0.18 * Math.sqrt(totalXp));
        Integer userLevelI = userLevelD.intValue();
        String userLevel = Integer.toString(userLevelI);
        return userLevel;
    }

    public String getUserLevelPersen(){
        Double userLevelD = ( 0.18 * Math.sqrt(totalXp));
        String userLevelPer = String.valueOf(userLevelD);
        userLevelPer = userLevelPer.substring(userLevelPer.indexOf("."),userLevelPer.indexOf(".")+3);
        return userLevelPer+"%";
    }

    public String getDefUser(Context context){
        SharedPreferences userPref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userName = userPref.getString("SH_USERNAME","");
        return userName;

    }
}
