package com.karnjang.firebasedemo.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.TheStoreActivity;
import com.karnjang.firebasedemo.models.ActiveTask;
import com.karnjang.firebasedemo.models.Store;
import com.karnjang.firebasedemo.models.Task;
import com.xw.repo.BubbleSeekBar;


import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");
    DatabaseReference dbUserRef = dbref.child("users");
    public long remainTime;
    String userName;

    ArrayList<Store> storeLists = new ArrayList<>();
    ArrayList<ActiveTask> activeTaskLists = new ArrayList<>();

    CustomTaskAdapter customTaskAdapter = new CustomTaskAdapter();

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View taskview = inflater.inflate(R.layout.fragment_task, container, false);
        final ListView listViewTask = (ListView) taskview.findViewById(R.id.listViewTask);
        final TextView headActiveTask = (TextView) taskview.findViewById(R.id.textView16);
        final TextView textSubCountdown = (TextView) taskview.findViewById(R.id.textView22);
        final TextView textTaskCountdown = (TextView) taskview.findViewById(R.id.textTaskCountdown);

        SharedPreferences userPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = userPref.getString("SH_USERNAME", "");
        //userName = "KARNAWAT";
        Log.i("info TaskFragment", "Start Listener");


        dbUserRef.child(userName).child("ACTIVETASK").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataTaskSnapshot) {
                for (DataSnapshot activeTaskSnapshot : dataTaskSnapshot.getChildren()) {
                    ActiveTask activeUserTask = activeTaskSnapshot.getValue(ActiveTask.class);
                    activeTaskLists.add(activeUserTask);
                    Log.i("check active task", "TASK get user's task DONE from: " + activeUserTask.getStoreId());

                    if (activeTaskLists.isEmpty()) {
                        headActiveTask.setText("Visit the store page to get the some task ");
                        headActiveTask.setTextSize(18);
                        textSubCountdown.setVisibility(View.GONE);
                        textTaskCountdown.setVisibility(View.GONE);
                    } else {
                        dbStoreRef.child(activeUserTask.getStoreId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Store activeStoreTask = new Store();
                                //Log.i("check active task", "TASK get store's task DONE from: " + activeStoreTask.getTASK().getTaskName());
                                Log.i("check active task", "TASK get store's task DONE from (SNAP): " + dataSnapshot.getValue());
                                //activeStoreList.add(activeStoreTask);

                                String storeId = (String) dataSnapshot.child("storeID").getValue();
                                String storeName = (String) dataSnapshot.child("storeName").getValue();
                                Task taskStore = dataSnapshot.child("TASK").getValue(Task.class);
                                activeStoreTask.setStoreID(storeId);
                                activeStoreTask.setStoreName(storeName);
                                activeStoreTask.setTASK(taskStore);
                                storeLists.add(activeStoreTask);

                                Log.i("TaskFragment", "check size activeTaskList:" + activeTaskLists.size());
                                Log.i("TaskFragment", "check size storeList:" + storeLists.size());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                    }


//.child("TASK")


                }
//                listViewTask.setAdapter(customTaskAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }



        });


        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 23);
        targetTime.set(Calendar.MINUTE, 59);

        new CountDownTimer(targetTime.getTimeInMillis() - System.currentTimeMillis(), 1000) {

            public void onTick(long millisUntilFinished) {

                int seconds = (int) (millisUntilFinished / 1000);
                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                textTaskCountdown.setText("Remaining Time : " + String.format("%02d", hours) + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));

                //textTaskTimeLeft.setText(millisUntilFinished/1000+"sec left...");
            }

            public void onFinish() {
                textTaskCountdown.setText("time out+");
            }

        }.start();

        new CountDownTimer(1750, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                listViewTask.setAdapter(customTaskAdapter);
            }

        }.start();


//        if (activeTaskLists.isEmpty()) {
//            headActiveTask.setText("Visit the store page to get the some task ");
//            headActiveTask.setTextSize(18);
//            textSubCountdown.setVisibility(View.GONE);
//            textTaskCountdown.setVisibility(View.GONE);
//        } else {
//            listViewTask.setAdapter(customTaskAdapter);
//            listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(getContext(), "check = " + activeTaskLists.get(i), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(), TheStoreActivity.class);
//                    intent.putExtra("storeID", activeTaskLists.get(i).getStoreId());
//                    startActivity(intent);
//
//                }
//            });
//        }


//        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "check = " + activeTaskLists.get(i), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), TheStoreActivity.class);
//                intent.putExtra("storeID", activeTaskLists.get(i).getStoreId());
//                startActivity(intent);
//
//            }
//        });
        return taskview;
    }

    public class CustomTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return activeTaskLists.size();
        }

        @Override
        public Object getItem(int i) {
            return activeTaskLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View tasklistview, ViewGroup viewGroup) {
            tasklistview = getLayoutInflater().inflate(R.layout.custom_task_listview, null);

            ActiveTask theActiveTask = activeTaskLists.get(i);
            Store theStore = storeLists.get(i);

            TextView textStoreInfo = tasklistview.findViewById(R.id.textStoreInfo);
            TextView textTaskName = tasklistview.findViewById(R.id.textTaskName);
            TextView textTaskDesc = tasklistview.findViewById(R.id.textTaskProgressBar);
            // final TextView textTaskTimeLeft = tasklistview.findViewById(R.id.textTaskTimeLeft);
            BubbleSeekBar progressTaskBar = tasklistview.findViewById(R.id.progressTaskBar);
            TextView textTaskReward = tasklistview.findViewById(R.id.textTaskReward);
            TextView textTaskStatus = tasklistview.findViewById(R.id.textTaskStatus);


            if (!activeTaskLists.isEmpty()) {
                if (theStore.getTASK() != null && theActiveTask != null) {
                    textStoreInfo.setText(theStore.getStoreID() + "_" + theStore.getStoreName());
                    textTaskName.setText(theStore.getTASK().getTaskName());
                    textTaskDesc.setText(theStore.getTASK().getTaskDetail() + ": " + theActiveTask.getCurrentCondition() + "/" + theStore.getTASK().getTaskConditionForCompleteTask());
                    textTaskReward.setText("+" + theStore.getTASK().getTaskExpReward() + "XP  / +" + theStore.getTASK().getTaskPointReward() + "PT");
//                    progressTaskBar.setMax(theStore.getTASK().getTaskConditionForCompleteTask());
//                    progressTaskBar.setProgress(theActiveTask.getCurrentCondition());
                    if (theActiveTask.getTaskStatus() == 0) {
                        textTaskStatus.setText("TASK ACTIVE");
                        textTaskStatus.setTextColor(Color.GREEN);
                    } else if (theActiveTask.getTaskStatus() == 1) {
                        textTaskStatus.setText("TASK DONE");
                        textTaskStatus.setTextColor(Color.RED);
                    } else {
                        textTaskStatus.setText("TASK STATUS ERROR");
                        textTaskStatus.setTextColor(Color.RED);
                    }

                    progressTaskBar.getConfigBuilder()
                            .min(0)
                            .max(theStore.getTASK().getTaskConditionForCompleteTask())
                            .progress(theActiveTask.getCurrentCondition())
                            .sectionCount(theStore.getTASK().getTaskConditionForCompleteTask())
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

                    progressTaskBar.setEnabled(false);
                } else {
                    //tasklistview.setVisibility(View.GONE);
                    textStoreInfo.setText(theStore.getStoreID() + "_" + theStore.getStoreName());
                    textTaskName.setText("Store Doesn't have the task right now");
                    textTaskDesc.setVisibility(View.GONE);
                    textTaskReward.setVisibility(View.GONE);
                    textTaskStatus.setVisibility(View.GONE);
                    progressTaskBar.setVisibility(View.GONE);

                }

            } else {

            }


//            TextView textTaskName = (TextView) tasklistview.findViewById(R.id.textTaskName);
//            TextView textTaskDesc = (TextView) tasklistview.findViewById(R.id.textTaskDesc);
//            textTaskName.setText(theStore.getStoreName()+"_"+theStore.getTASK().getTaskName());
//            textTaskDesc.setText(theStore.getTASK().getTaskDetail());


            return tasklistview;
        }
    }

}
