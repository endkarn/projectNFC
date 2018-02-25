package com.karnjang.firebasedemo.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");


    public UserInfoFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        final TextView textInfoUsername = (TextView) view.findViewById(R.id.textInfoUsername);
        final TextView textInfoLevel = (TextView) view.findViewById(R.id.textInfoLevel);
        SharedPreferences userPref = this.getActivity().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        final String userName = userPref.getString("SH_USERNAME","");
        Log.i("Info", "USERNAME FROM Fragment UserInfo = "+userName);

        dbUserRef.child(userName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.i("dataUserInfo","dataUserInfo+++"+dataSnapshot);
                        User user = dataSnapshot.getValue(User.class);
                        Log.i("dataUserInfo","dataUserInfo userClass+++"+user);
                        textInfoUsername.setText(user.getUsername());
                        textInfoLevel.setText("Level "+user.getUserLevel());





                        //Toast.makeText(getApplicationContext(), "GETTING DATA", Toast.LENGTH_SHORT).show();
//                        TextView userText = (TextView) findViewById(R.id.textUsername);
//                        int userLevel = user.getTotalXp() / 100;
//                        int userTotalXp = user.getTotalXp() % 100;
//                        userText.setText("Username : " + user.getUsername() + "\n Total EXP : " + user.getTotalXp() + "\n User Level : " + userLevel);
//                        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
//                        mProgressBar.setProgress(userTotalXp);

                    } else {
                        //Toast.makeText(getApplicationContext(), "NO DATA OF THIS USERNAME", Toast.LENGTH_SHORT).show();

                    }

                }



            @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//        if (userName != null){
//           // String userName = userBundle.getString("userName");
//            //int userPoint = bundle.getInt("userPoint");
//           // int userXp = userBundle.getInt("userXp");
//          //  int userLv = userXp/100;
//          //  Log.i("Info", "USERPOINT FG = "+userPoint);
//          //  Log.i("Info", "USEREXP FG= "+userXp);
//
//            textUsername.setText("username: "+userName);
//           // totalPoint.setText("totalpoints: "+userPoint);
//           // textUserLv.setText(""+userLv);
//
//
//
//
//        } else {
//            textUsername.setText("");
//        }

        return view;
    }

}
