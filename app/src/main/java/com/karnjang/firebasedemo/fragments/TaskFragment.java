package com.karnjang.firebasedemo.fragments;


import android.content.Intent;
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
import com.karnjang.firebasedemo.models.Store;
import com.karnjang.firebasedemo.models.Task;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");
    public long remainTime;


    String[] TASKNAME = {"TASK00", "TASK01", "TASK02", "TASK03", "TASK04", "TASK05", "TASK06"};
    String[] TASKDESC = {"DESC00", "DESC01", "DESC02", "DESC03", "DESC04", "DESC05", "DESC06"};
    ArrayList<Store> storeLists = new ArrayList<>();

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View taskview = inflater.inflate(R.layout.fragment_task, container, false);
        // Inflate the layout for this fragment

        final ListView listViewTask = (ListView) taskview.findViewById(R.id.listViewTask);

        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 23);
        targetTime.set(Calendar.MINUTE, 59);

        new CountDownTimer(targetTime.getTimeInMillis()-System.currentTimeMillis(), 1000) {

            public void onTick(long millisUntilFinished) {
                remainTime = millisUntilFinished/1000;
            }

            public void onFinish() {
                remainTime = 0;
            }

        }.start();

        Log.i("info TaskFragment","Start Listener");
        dbStoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                Log.i("info StoreFragment", "Adding Store Done");


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

        Log.i("Info", "SET ADAPTER TO TASK FRAGMENT");


        return taskview;
    }

    public class CustomTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return storeLists.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }


        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View tasklistview, ViewGroup viewGroup) {
            tasklistview = getLayoutInflater().inflate(R.layout.custom_task_listview, null);
            Store theStore = storeLists.get(i);
            TextView textTaskName = (TextView) tasklistview.findViewById(R.id.taskTextName);
            TextView textTaskDesc = (TextView) tasklistview.findViewById(R.id.taskTextDesc);
            final TextView textTaskCountDown = (TextView) tasklistview.findViewById(R.id.taskTextCountDown);
            ProgressBar progressTaskBar = (ProgressBar) tasklistview.findViewById(R.id.progressTaskBar);

            textTaskName.setText(theStore.getStoreName()+"_"+theStore.getTASK().getTaskName());
            textTaskDesc.setText(theStore.getTASK().getTaskDetail());

            Calendar targetTime = Calendar.getInstance();
            targetTime.set(Calendar.HOUR_OF_DAY, 23);
            targetTime.set(Calendar.MINUTE, 59);

            new CountDownTimer(targetTime.getTimeInMillis()-System.currentTimeMillis(), 1000) {

                public void onTick(long millisUntilFinished) {
                    textTaskCountDown.setText(millisUntilFinished/1000+"sec left...");
                }

                public void onFinish() {
                    textTaskCountDown.setText("time out+");
                }

            }.start();


            textTaskCountDown.setText(remainTime+"sec left...");

            return tasklistview;
        }
    }

}
