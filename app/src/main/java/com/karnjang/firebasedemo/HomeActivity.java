package com.karnjang.firebasedemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.fragments.HomeFragment;
import com.karnjang.firebasedemo.fragments.MoreFragment;
import com.karnjang.firebasedemo.fragments.RankingFragment;
import com.karnjang.firebasedemo.fragments.StoreFragment;
import com.karnjang.firebasedemo.fragments.TaskFragment;
import com.karnjang.firebasedemo.fragments.UserInfoFragment;
import com.karnjang.firebasedemo.models.User;

/**
 * Created by ssppy on 24-Nov-17.
 */

public class HomeActivity  extends AppCompatActivity{
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        final UserInfoFragment fragmentUser = new UserInfoFragment();
//        final Bundle userBundle = new Bundle();
//        final Bundle bundle = getIntent().getExtras();
//        String yourName = bundle.getString("username");
//        //FOR TESTING ONLY
//        yourName = "KARNAWAT";
//        userBundle.putString("userName",yourName);
//
//
//            dbUserRef.child(yourName).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        User user = dataSnapshot.getValue(User.class);
//                        int yourUserPoint = user.getTotalPoints();
//                        int yourUserExp = user.getTotalXp();
//                        String yourUserName = user.getUsername();
//                        Log.i("Info FROM Home", "USERPOINT = "+yourUserPoint);
//                        Log.i("Info FROM Home", "USEREXP = "+yourUserExp);
//                        Log.i("Info FROM Home", "USERNAME = "+yourUserName);
//
//                        userBundle.putString("userName",yourUserName);
//                        userBundle.putInt("userPoint",yourUserPoint);
//                        userBundle.putInt("userXp", yourUserExp);
//
//                        Toast.makeText(getApplicationContext(), "GETTING DATA", Toast.LENGTH_SHORT).show();
////                        TextView userText = (TextView) findViewById(R.id.textUsername);
////                        int userLevel = user.getTotalXp() / 100;
////                        int userTotalXp = user.getTotalXp() % 100;
////                        userText.setText("Username : " + user.getUsername() + "\n Total EXP : " + user.getTotalXp() + "\n User Level : " + userLevel);
////                        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
////                        mProgressBar.setProgress(userTotalXp);
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "NO DATA OF THIS USERNAME", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });






//        fragmentUser.setArguments(userBundle);


        transaction.replace(R.id.layout_fragment_container,homeFragment);
        transaction.replace(R.id.fragment2,fragmentUser);
        transaction.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_home:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction transaction0 = getSupportFragmentManager().beginTransaction();
                        transaction0.replace(R.id.layout_fragment_container,homeFragment);
                        transaction0.commit();
                        break;
                    case R.id.action_store:
                        StoreFragment storeFragment = new StoreFragment();
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.layout_fragment_container,storeFragment);
                        transaction1.commit();
                        break;
                    case R.id.action_task:
                        TaskFragment taskFragment = new TaskFragment();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.layout_fragment_container,taskFragment);
                        transaction2.commit();
                        break;
                    case R.id.action_ranking:
                        RankingFragment rankingFragment = new RankingFragment();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.layout_fragment_container,rankingFragment);
                        transaction3.commit();
                        break;
                    case R.id.action_more:
                        MoreFragment moreFragment = new MoreFragment();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.layout_fragment_container,moreFragment);
                        transaction4.commit();
                        break;


                }
                return true;
            }
        });
    }
}
