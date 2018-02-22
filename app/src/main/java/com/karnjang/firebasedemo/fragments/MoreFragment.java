package com.karnjang.firebasedemo.fragments;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.models.Action;
import com.karnjang.firebasedemo.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    User myUser = new User();
    ArrayList<Action> listAction = new ArrayList<>();
    Action newAction = new Action("sad123ds","+100exp +100pt",
            "SC007","QUEST COMPLETE","today");


    public MoreFragment() {
        // Required empty public constructor

        listAction.add(newAction);
        listAction.add(newAction);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View moreFragment = inflater.inflate(R.layout.fragment_more, container, false);
        ListView listAction = (ListView) moreFragment.findViewById(R.id.listViewAction);

        String username = myUser.getDefUser(this.getContext());
        Log.i("INFO MORE","SHOW MEEEE "+username);



        CustomActionAdapter customActionAdapter = new CustomActionAdapter();
        listAction.setAdapter(customActionAdapter);


        return moreFragment;
    }

    public class CustomActionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listAction.size();
        }

        @Override
        public Object getItem(int i) {
            return listAction.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View actionlistview, ViewGroup viewGroup) {
            actionlistview = getLayoutInflater().inflate(R.layout.custom_action_listview,null);
            TextView textActionId = actionlistview.findViewById(R.id.textActionId);
            TextView textActionResult = actionlistview.findViewById(R.id.textActionResult);
            TextView textActionStore = actionlistview.findViewById(R.id.textActionStore);
            TextView textActionDetail = actionlistview.findViewById(R.id.textActionDetail);
            TextView textActionTimeStamp = actionlistview.findViewById(R.id.textActionTimeStamp);

            Action action = listAction.get(i);
            textActionId.setText(action.getActionId());
            textActionResult.setText(action.getActionResult());
            textActionStore.setText(action.getActionStore());
            textActionDetail.setText(action.getActionDetail());
            textActionTimeStamp.setText(action.getActionTimeStamp());

            return actionlistview;
        }
    }

}
