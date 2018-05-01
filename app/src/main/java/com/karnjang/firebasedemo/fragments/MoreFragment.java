package com.karnjang.firebasedemo.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.karnjang.firebasedemo.models.User;
import com.karnjang.firebasedemo.models.UserAct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");

    User myDefUser = new User();
    ArrayList<UserAct> actionArrayList = new ArrayList<>();
    ArrayList<String> getKeyArrayList = new ArrayList<>();

    UserAct testAction1 = new UserAct();

    CustomActionAdapter customActionAdapter = new CustomActionAdapter();

    TextView textUserLevel,textUsername,textUserTotalPoint,textUserExp,textUserLevelCard;
    CircleImageView imageProfileImage;


    public MoreFragment() {
        // Required empty public constructor
//        testAction1.setActionResult("+100XP");
//        testAction1.setActionStore("SC09");
//        testAction1.setActionDetail("Complete Task");
//        testAction1.setActionTimeStamp(" ");
//        getKeyArrayList.add("akjhfdhgkaskdjh");
//        getKeyArrayList.add("akjhfdhgkaskdjh");
//        getKeyArrayList.add("akjhfdhgkaskdjh");
//        actionArrayList.add(testAction1);
//        actionArrayList.add(testAction1);
//        actionArrayList.add(testAction1);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View moreFragment = inflater.inflate(R.layout.fragment_more, container, false);
        final ListView listAction =  moreFragment.findViewById(R.id.listViewAction);

        textUserLevel = moreFragment.findViewById(R.id.textUserLevel);
        textUsername = moreFragment.findViewById(R.id.textUsername);
        textUserTotalPoint = moreFragment.findViewById(R.id.textUserTotalPoint);
        textUserExp = moreFragment.findViewById(R.id.textUserExp);
        textUserLevelCard = moreFragment.findViewById(R.id.textUserLevelCard);

        imageProfileImage = moreFragment.findViewById(R.id.imageProfileImage);


        String username = myDefUser.getDefUser(this.getContext());
        Log.i("INFO MORE","SHOW MEEEE "+username);

        User getuser = new User();

        //get userinfo
        dbUserRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User getuser = dataSnapshot.getValue(User.class);
                Log.i("MORE INFO","my user data snap"+dataSnapshot.getValue());
                textUserLevel.setText(getuser.getUserLevel());
                textUsername.setText(getuser.getUsername());
                textUserTotalPoint.setText(""+getuser.getTotalPoints());
                textUserExp.setText(""+getuser.getTotalXp());
                textUserLevelCard.setText(getuser.WTFjustaPersent());

                //set image from url facebook
                Picasso.with(getContext()).load(getuser.getPictureProfile()).fit().into(imageProfileImage);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        //getuser userActionTasks
        //username = "KARNAWAT";
        dbUserRef.child(username).child("ACTIONS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot actionSnapshot : dataSnapshot.getChildren()){
                    UserAct userAction = actionSnapshot.getValue(UserAct.class);
                    actionArrayList.add(userAction);
                    Log.i("MORE INFO","useraction "+userAction.getActionDetail());
                    Log.i("MORE INFO","KEYYYYYYYYY "+actionSnapshot.getKey());
//                    Log.i("MORE INFO","actionSnapshot getvalue "+actionSnapshot.getValue());
                    getKeyArrayList.add(actionSnapshot.getKey());

                }
                listAction.setAdapter(customActionAdapter);
                Log.i("MORE INFO","size actionlists"+actionArrayList.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });



        listAction.setAdapter(customActionAdapter);


        return moreFragment;
    }

    public class CustomActionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return actionArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return actionArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View actionlistview, ViewGroup viewGroup) {
            actionlistview = getLayoutInflater().inflate(R.layout.custom_action_listview,null);
            TextView textActionId = actionlistview.findViewById(R.id.textActionId);
            TextView textActionResult = actionlistview.findViewById(R.id.textActionResult);
            TextView textActionStore = actionlistview.findViewById(R.id.textActionStore);
            TextView textActionDetail = actionlistview.findViewById(R.id.textActionDetail);
            TextView textActionTimeStamp = actionlistview.findViewById(R.id.textActionTimeStamp);

            UserAct aUserAct = (UserAct) getItem(getCount() - 1 - i);

            textActionId.setText("ref : "+getKeyArrayList.get(getCount() - 1 - i));
            textActionResult.setText(aUserAct.getActionResult());
            textActionStore.setText("@"+aUserAct.getActionStore());
            textActionDetail.setText(aUserAct.getActionDetail());
            textActionTimeStamp.setText(aUserAct.getActionTimeStamp());

          //  Log.i("check userAct","getView userAct"+ actionArrayList.get(i));



            return actionlistview;
        }
    }




}
