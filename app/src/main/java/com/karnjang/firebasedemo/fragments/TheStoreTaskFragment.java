package com.karnjang.firebasedemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.models.ActiveTask;
import com.karnjang.firebasedemo.models.Store;
import com.karnjang.firebasedemo.models.Task;
import com.karnjang.firebasedemo.models.User;
import com.xw.repo.BubbleSeekBar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreTaskFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");
    DatabaseReference dbUserRef = dbref.child("users");
    Long longUserProgress;
    Long longTaskStatus;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public TheStoreTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thestoretaskview = inflater.inflate(R.layout.fragment_the_store_task, container, false);
        final TextView textTaskName = (TextView) thestoretaskview.findViewById(R.id.textTaskName) ;
        //final ProgressBar progressTaskBar = (ProgressBar) thestoretaskview.findViewById(R.id.progressTaskBar);
        final BubbleSeekBar progresTaskBar = (BubbleSeekBar) thestoretaskview.findViewById(R.id.progressTaskBar);
        final TextView textProgressBar = (TextView) thestoretaskview.findViewById(R.id.textTaskProgressBar);
        final TextView textTaskTimeLeft = (TextView) thestoretaskview.findViewById(R.id.textTaskTimeLeft);
        final TextView textTaskRewards = (TextView) thestoretaskview.findViewById(R.id.textTaskRewards);
        final TextView textTaskStatus = (TextView) thestoretaskview.findViewById(R.id.textTaskStatus);
        SharedPreferences userPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        final String userName = userPref.getString("SH_USERNAME",null);
        Log.i("STORE TASK####","username from sh"+userName);
        final String storeId = getActivity().getIntent().getExtras().getString("storeID");
       // String storeName = getActivity().getIntent().getExtras().getString("storeName");

        Log.i("TheStoreTask INFO","Check StoreID From Activity "+storeId);
        dbStoreRef.child(storeId).child("TASK").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot taskSnapshot) {
                if (taskSnapshot.exists()) {
                    Log.i("TheStoreTask INFO","Check DATA taskSnapshot "+taskSnapshot.getValue());

                    final Task oneStoreTask = taskSnapshot.getValue(Task.class);
                    textTaskName.setText(oneStoreTask.getTaskName());
                    textTaskRewards.setText("Rewards XP+" + oneStoreTask.getTaskExpReward() + "/ Point+" + oneStoreTask.getTaskPointReward());
                    final Integer taskProgress = oneStoreTask.getTaskConditionForCompleteTask();


                    dbUserRef.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userSnapshot) {

                            longUserProgress = (Long) userSnapshot.child("ACTIVETASK").child(storeId).child("currentCondition").getValue();
                            longTaskStatus = (Long) userSnapshot.child("ACTIVETASK").child(storeId).child("taskStatus").getValue();
                            int userProgress = longUserProgress.intValue();
                            int intTaskStatus = longTaskStatus.intValue();

                            if(intTaskStatus == 0){
                                textTaskStatus.setText("TASK ACTIVE");
                                textTaskStatus.setTextColor(Color.GREEN);
                            }else if (intTaskStatus == 1) {
                                textTaskStatus.setText("TASK DONE");
                                textTaskStatus.setTextColor(Color.RED);
                            }else {
                                textTaskStatus.setText("TASK STATUS ERROR");
                                textTaskStatus.setTextColor(Color.RED);
                            }

                            if (userProgress == taskProgress && intTaskStatus == 0){
                                User user = userSnapshot.getValue(User.class);
                                Task task = taskSnapshot.getValue(Task.class);
                                ActiveTask userTask = userSnapshot.child("ACTIVETASK").child(storeId).getValue(ActiveTask.class);



                                userTask.setTaskStatus(1);
                                userTask.setStoreId(storeId);
                                dbUserRef.child(userName).child("totalPoints").setValue(user.getTotalPoints()+task.getTaskPointReward());
                                dbUserRef.child(userName).child("totalXp").setValue(user.getTotalXp()+task.getTaskExpReward());
                                dbUserRef.child(userName).child("ACTIVETASK").child(storeId).setValue(userTask);

//                                Map<String,Object> taskMap = new HashMap<String,Object>();
//                                taskMap.put("currentCondition",0);
//                                dbUserRef.child(userName).child("ACTIVETASK").child(storeId).child("currentCondition").updateChildren(taskMap);

                                Log.i("StoreTask","Task is DONE and get Rewards");
//                                SystemClock.sleep(5000);
//                                getActivity().finish();
                            }


                            textProgressBar.setText(oneStoreTask.getTaskDetail()+" "+ userProgress + "/" + taskProgress);
                            //progressTaskBar.setProgress(userProgress);

                            progresTaskBar.getConfigBuilder()
                                    .min(0)
                                    .max(taskProgress)
                                    .progress(userProgress)
                                    .sectionCount(taskProgress)
                                    .trackColor(ContextCompat.getColor(getActivity(), R.color.color_gray))
                                    .secondTrackColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
                                    .thumbColor(ContextCompat.getColor(getActivity(), R.color.color_blue))
                                    .showSectionText()
                                    .sectionTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                                    .sectionTextSize(18)
                                    .showThumbText()
                                    .thumbTextColor(ContextCompat.getColor(getActivity(), R.color.color_red))
                                    .thumbTextSize(18)
                                    .bubbleColor(ContextCompat.getColor(getActivity(), R.color.color_red))
                                    .bubbleTextSize(18)
                                    .showSectionMark()
                                    .seekStepSection()
                                    .touchToSeek()
                                    .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                                    .build();

                            progresTaskBar.setEnabled(false);

//                            completeTask() Function below



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //do nothing
                        }
                    });





                    Calendar targetTime = Calendar.getInstance();
                    targetTime.set(Calendar.HOUR_OF_DAY, 23);
                    targetTime.set(Calendar.MINUTE, 59);

                    new CountDownTimer(targetTime.getTimeInMillis()-System.currentTimeMillis(), 1000) {

                        public void onTick(long millisUntilFinished) {
                            textTaskTimeLeft.setText("seconds remaining: " + millisUntilFinished / 1000);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            textTaskTimeLeft.setText("done!");
                        }

                    }.start();

                    Log.i("TheStoreTask INFO","Check DATA TASK "+oneStoreTask.getTaskDetail());
                    Log.i("TheStoreTask INFO","Check DATA TASK "+oneStoreTask.getTaskName());
                    //Log.i("TheStoreTask INFO","Check DATA StoreID "+taskSnapshot.child("taskConditionForCompleteTask").getValue());

                    //Integer taskConditionForCompleteTask = (Integer) taskSnapshot.child("taskConditionForCompleteTask").getValue();

                    //Log.i("TheStoreTask INFO","Check DATA StoreID "+taskConditionForCompleteTask);

//                    String taskDetail = (String)  taskSnapshot.child("taskDetail").getValue();
//                    int taskExpReward = (int)  taskSnapshot.child("taskExpReward").getValue();
//                    String taskName = (String)  taskSnapshot.child("taskName").getValue();
//                    int taskPointReward = (int)  taskSnapshot.child("taskPointReward").getValue();

//                    Log.i("TheStoreTask INFO","Check DATA StoreID"+taskSnapshot.getValue());
//                    Log.i("TheStoreTask INFO","Check DATA StoreID"+oneStoreTask);


//                    String storeId = (String) dataSnapshot.child("storeID").getValue();
//                    String storeName = (String) dataSnapshot.child("storeName").getValue();
//                    oneStore.setStoreID(storeId);
//                    oneStore.setStoreName(storeName);
//                    Log.i("TheStoreTask INFO","Check DATA StoreID"+taskDetail);
//                    Log.i("TheStoreTask INFO","Check DATA StoreName"+taskName);

                } else {
                    Log.i("TheStoreTask INFO", "No Data From Firebase");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.i("TheStoreTask INFO","position " +storeId);
        return thestoretaskview;
    }

    public void completeTask(User user,Task task){


    }

}
