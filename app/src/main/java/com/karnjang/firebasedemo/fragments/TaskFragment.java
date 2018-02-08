package com.karnjang.firebasedemo.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import org.w3c.dom.Text;

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

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View taskview = inflater.inflate(R.layout.fragment_task, container, false);
        final ListView listViewTask = (ListView) taskview.findViewById(R.id.listViewTask);

        SharedPreferences userPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = userPref.getString("SH_USERNAME","");

        Log.i("info TaskFragment","Start Listener");
        dbStoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    Log.i("info TaskFragment", "data snapshot storekey " + storeSnapshot.getKey());
                    Log.i("info TaskFragment", "data snapshot value " + storeSnapshot.getValue());
                    Store oneStore = new Store();
                    String storeId = (String) storeSnapshot.child("storeID").getValue();
                    String storeName = (String)  storeSnapshot.child("storeName").getValue();
                    Task taskStore = storeSnapshot.child("TASK").getValue(Task.class);
                    Log.i("info TaskFragment", "data snapshot value " + taskStore   );
                    oneStore.setStoreID(storeId);
                    oneStore.setStoreName(storeName);
                    oneStore.setTASK(taskStore);
                    storeLists.add(oneStore);
                    Log.i("info TaskFragment", "Store Added" + storeId);

                }
                dbUserRef.child(userName).child("ACTIVETASK").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataTaskSnapshot) {
                        for(DataSnapshot activeTaskSnapshot : dataTaskSnapshot.getChildren()){
                            ActiveTask activeTask = activeTaskSnapshot.getValue(ActiveTask.class);
                            activeTaskLists.add(activeTask);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                CustomTaskAdapter customTaskAdapter = new CustomTaskAdapter();
                listViewTask.setAdapter(customTaskAdapter);
                listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getContext(), "check = " + storeLists.get(i), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), TheStoreActivity.class);
                        intent.putExtra("storeID", storeLists.get(i).getStoreID());
                        startActivity(intent);

                    }
                });


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            });


        return taskview;
    }

    public class CustomTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return storeLists.size();
        }

        @Override
        public Object getItem(int i) {
            return storeLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View tasklistview, ViewGroup viewGroup) {
            tasklistview = getLayoutInflater().inflate(R.layout.custom_task_listview, null);

            if(!activeTaskLists.isEmpty()){
                Store theStore = storeLists.get(i);
                ActiveTask theActiveTask = activeTaskLists.get(i);

                TextView textStoreInfo = tasklistview.findViewById(R.id.textStoreInfo);
                TextView textTaskName = tasklistview.findViewById(R.id.textTaskName);
                TextView textTaskDesc = tasklistview.findViewById(R.id.textTaskDesc);
                final TextView textTaskTimeLeft = tasklistview.findViewById(R.id.textTaskTimeLeft);
                ProgressBar progressTaskBar = tasklistview.findViewById(R.id.progressTaskBar);
                TextView textTaskReward = tasklistview.findViewById(R.id.textTaskReward);
                TextView textTaskStatus = tasklistview.findViewById(R.id.textTaskStatus);

                if(theStore != null && theActiveTask != null){
                    textStoreInfo.setText(theStore.getStoreID()+"_"+theStore.getStoreName());
                    textTaskName.setText(theStore.getTASK().getTaskName());
                    textTaskDesc.setText(theStore.getTASK().getTaskDetail() + ": "+theActiveTask.getCurrentCondition()+ "/" + theStore.getTASK().getTaskConditionForCompleteTask());
                    textTaskReward.setText("+"+theStore.getTASK().getTaskExpReward()+"XP  / +"+theStore.getTASK().getTaskPointReward()+"PT");
                    progressTaskBar.setMax(theStore.getTASK().getTaskConditionForCompleteTask());
                    progressTaskBar.setProgress(theActiveTask.getCurrentCondition());
                    if(theActiveTask.getTaskStatus() == 0){
                        textTaskStatus.setText("TASK ACTIVE");
                        textTaskStatus.setTextColor(Color.GREEN);
                    }else if (theActiveTask.getTaskStatus() == 1) {
                        textTaskStatus.setText("TASK DONE");
                        textTaskStatus.setTextColor(Color.RED);
                    }else {
                        textTaskStatus.setText("TASK STATUS ERROR");
                        textTaskStatus.setTextColor(Color.RED);
                    }
                }

                Calendar targetTime = Calendar.getInstance();
                targetTime.set(Calendar.HOUR_OF_DAY, 23);
                targetTime.set(Calendar.MINUTE, 59);

                new CountDownTimer(targetTime.getTimeInMillis()-System.currentTimeMillis(), 1000) {

                    public void onTick(long millisUntilFinished) {
                        textTaskTimeLeft.setText(millisUntilFinished/1000+"sec left...");
                    }

                    public void onFinish() {
                        textTaskTimeLeft.setText("time out+");
                    }

                }.start();
            }













//            TextView textTaskName = (TextView) tasklistview.findViewById(R.id.textTaskName);
//            TextView textTaskDesc = (TextView) tasklistview.findViewById(R.id.textTaskDesc);
//            textTaskName.setText(theStore.getStoreName()+"_"+theStore.getTASK().getTaskName());
//            textTaskDesc.setText(theStore.getTASK().getTaskDetail());








            return tasklistview;
        }
    }

}
