package com.karnjang.firebasedemo;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.karnjang.firebasedemo.fragments.TheStoreItemListFragment;

public class TheStoreActivity extends AppCompatActivity {
    private TextView textTheStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Log.i("Info", "Activity Store");
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int storePosition = bundle.getInt("storePosition");
       // Log.i("Info", "Store Position = "+storePosition);

        setContentView(R.layout.activity_the_store);

       // textTheStore = (TextView) findViewById(R.id.textTheStoreName);
       // textTheStore.setText("Store Position is " +storePosition);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TheStoreItemListFragment theStoreItemListFragment = new TheStoreItemListFragment();

        transaction.replace(R.id.layout_fragment_container2, theStoreItemListFragment);
        transaction.commit();

    }
}
