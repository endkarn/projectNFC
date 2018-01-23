package com.karnjang.firebasedemo.fragments;


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
import com.karnjang.firebasedemo.models.Store;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreDetailFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");

    public TheStoreDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thestoreview = inflater.inflate(R.layout.fragment_the_store_detail,container,false);
        final TextView textTheStoreId = (TextView) thestoreview.findViewById(R.id.textTheStoreId);
        final TextView textTheStoreName = (TextView) thestoreview.findViewById(R.id.textTheStoreName);

        Log.i("TheStoreDetail INFO","Start Getting Data From The Store From Firebase" +dbStoreRef);

       String storeId = getActivity().getIntent().getExtras().getString("storeID");

        Log.i("TheStoreDetail INFO","Check StoreID From Activity "+storeId);
//        dbStoreRef.child(storeId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    Store oneStore = new Store();
//                    String storeId = (String) dataSnapshot.child("storeID").getValue();
//                    String storeName = (String) dataSnapshot.child("storeName").getValue();
//                    oneStore.setStoreID(storeId);
//                    oneStore.setStoreName(storeName);
//                    Log.i("TheStoreDetail INFO","Check DATA StoreID"+storeId);
//                    Log.i("TheStoreDetail INFO","Check DATA StoreName"+storeName);
//
//                    textTheStoreId.setText("STORE_ID__"+storeId);
//                    textTheStoreName.setText("STORE_NAME__"+storeName);
//                }else {
//                    Log.i("TheStoreDetail INFO","No Data From Firebase");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        Log.i("StoreDetail INFO","position"+storeId);


        return thestoreview;
    }

}
