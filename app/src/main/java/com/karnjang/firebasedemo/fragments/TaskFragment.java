package com.karnjang.firebasedemo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.TheStoreActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    String[] TASKNAME = {"TASK00", "TASK01", "TASK02", "TASK03", "TASK04", "TASK05", "TASK06"};
    String[] TASKDESC = {"DESC00", "DESC01", "DESC02", "DESC03", "DESC04", "DESC05", "DESC06"};

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View taskview = inflater.inflate(R.layout.fragment_task, container, false);
        // Inflate the layout for this fragment

        ListView listViewTask = (ListView) taskview.findViewById(R.id.listViewTask);
        CustomTaskAdapter customTaskAdapter = new CustomTaskAdapter();
        listViewTask.setAdapter(customTaskAdapter);
        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"check = "+i, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), TheStoreActivity.class);
                intent.putExtra("storePosition",i);

                startActivity(intent);
            }
        });
        Log.i("Info", "SET ADAPTER TO TASK FRAGMENT");

        return taskview;
    }

    public class CustomTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return TASKNAME.length;
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
            TextView textTaskName = (TextView) tasklistview.findViewById(R.id.taskTextName);
            TextView textTaskDesc = (TextView) tasklistview.findViewById(R.id.taskTextDesc);
            TextView textTaskCountDown = (TextView) tasklistview.findViewById(R.id.taskTextCountDown);

            textTaskName.setText(TASKNAME[i]);
            textTaskDesc.setText(TASKDESC[i]);
            Log.i("Info", "TASK VALUE NAME" + TASKNAME[i] + " DESC " + TASKDESC[i]);
            textTaskCountDown.setText("11:59:02 Left..");
            return tasklistview;
        }
    }

}
