package com.karnjang.firebasedemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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
import com.karnjang.firebasedemo.models.Task;
import com.karnjang.firebasedemo.models.TaskFeed;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbFeedRef = dbref.child("FEEDS");


    ArrayList<TaskFeed> feedList = new ArrayList<>();
    TaskFeed testFeed = new TaskFeed();

    CustomAdapter customAdapter = new CustomAdapter();



    public HomeFragment() {
        // Required empty public constructor
        testFeed.setFeedDetail("HARDCODE !!  For testing ONLY");
        testFeed.setFeedStore("STORE001");
        testFeed.setFeedUsername("สมพงษ์");
//        SimpleDateFormat stampTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formated = stampTime.format(new Date());
        testFeed.setFeedTimeStamp("TIME");

      //  feedList.add(testFeed);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final ListView listViewFeed = (ListView) view.findViewById(R.id.listViewFeed);
            TextView titleFeed = (TextView) view.findViewById(R.id.textView20);


        dbFeedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot feedSnapshot : dataSnapshot.getChildren()){
                    TaskFeed taskFeed = feedSnapshot.getValue(TaskFeed.class);
                    feedList.add(taskFeed);
                }
                listViewFeed.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return view;

    }

    public class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return feedList.size();
        }

        @Override
        public Object getItem(int i) {
            return feedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_feed_listview, null);
            final TextView textFeedDes = view.findViewById(R.id.textFeedDescription);
            final ImageView imageView = view.findViewById(R.id.imageProfileImage);

            final TaskFeed aTaskFeed = (TaskFeed) getItem(getCount() - 1 - i);


            //Get Profile PictureURL
            DatabaseReference dbUserRef = dbref.child("users");
            dbUserRef.child(aTaskFeed.getFeedUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String imgURL = (String)  dataSnapshot.child("pictureProfile").getValue();
                    Picasso.with(getContext()).load(imgURL).fit().into(imageView);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //Convent storeID -> storeName
            DatabaseReference dbStoreRef = dbref.child("STORE");
            dbStoreRef.child(aTaskFeed.getFeedStore()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        aTaskFeed.setFeedStore((String)dataSnapshot.child("storeName").getValue());

                    }else {
                       // aTaskFeed.setFeedStore("ERROR GET STORE NAME");
                    }
                    String s = "<b> "+ aTaskFeed.getFeedUsername() +" </b> " +aTaskFeed.getFeedDetail() +" from <b>"+aTaskFeed.getFeedStore()+"</b>";
                    textFeedDes.setText(Html.fromHtml(s));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            return view;
        }
    }

}
