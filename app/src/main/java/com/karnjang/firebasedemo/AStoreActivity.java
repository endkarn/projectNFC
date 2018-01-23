package com.karnjang.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AStoreActivity extends AppCompatActivity {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String storeId = getIntent().getExtras().getString("storeID");
        Log.i("INFO", "STORE ID is " + storeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astore);

    }
}