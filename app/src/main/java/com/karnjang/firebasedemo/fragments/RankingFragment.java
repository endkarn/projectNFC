package com.karnjang.firebasedemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.TheStoreActivity;
import com.karnjang.firebasedemo.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");


    String spUsername = "KARNAWAT";

    ArrayList<User> userArrayList = new ArrayList<>();

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rankview = inflater.inflate(R.layout.fragment_ranking, container, false);
        final ListView listViewRank = (ListView) rankview.findViewById(R.id.listViewRank);

        dbUserRef.orderByChild("totalXp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Log.i("USER info","DATA[] "+userSnapshot.getValue());
                    User oneUser = userSnapshot.getValue(User.class);
                    Log.i("USER iiiii","data " +oneUser.getUsername() + " " +oneUser.getTotalXp());
                    userArrayList.add(oneUser);
                }

                CustomRankAdapter customRankAdapter = new CustomRankAdapter();
                listViewRank.setAdapter(customRankAdapter);

                //Collections.reverse((List<?>) listViewRank);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rankview;
    }

    public class CustomRankAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return userArrayList.size();
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
        public View getView(int i, View ranklistview, ViewGroup viewGroup) {
            ranklistview = getLayoutInflater().inflate(R.layout.custom_rank_listview, null);
            TextView textRankName = (TextView) ranklistview.findViewById(R.id.rankTextName);
            TextView textRankLevel = (TextView) ranklistview.findViewById(R.id.rankTextLevel);
            TextView textRankCount = (TextView) ranklistview.findViewById(R.id.textRankCount);
            ImageView imageRank = (ImageView) ranklistview.findViewById(R.id.imageRank);

            textRankCount.setText(""+(i+1));

            if(i == 0){
                imageRank.setImageResource(R.drawable.gold_thophy);
            }else if (i == 1){
                imageRank.setImageResource(R.drawable.sliver_thophy);
            }else if (i == 2){
                imageRank.setImageResource(R.drawable.bronze_thophy);
            }else {
                imageRank.setVisibility(View.INVISIBLE);
            }

            //Sorting by DESC
            User theUser = userArrayList.get(getCount() - 1 - i);
            textRankName.setText(theUser.getUsername());
//            if(theUser.getUsername().equals(spUsername)){
//                textRankName.setTextColor(Color.GREEN);
//                textRankLevel.setTextColor(Color.GREEN);
//            }
            textRankLevel.setText(theUser.getUserLevel());
            return ranklistview;
        }
    }

}
