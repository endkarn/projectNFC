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
import android.widget.ImageView;
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
import com.karnjang.firebasedemo.models.Store;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");

    int[] IMAGES = {R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2};
//    String[] NAMES = {"STORE00", "STORE01", "STORE02", "STORE03", "STORE04", "STORE05", "STORE06"};
//    String[] DESCS = {"DESC00", "DESC01", "DESC02", "DESC03", "DESC04", "DESC05", "DESC06"};
    ArrayList<Store> storeLists = new ArrayList<>();


    public StoreFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View storeview = inflater.inflate(R.layout.fragment_store, container, false);
        // Inflate the layout for this fragment

        final ListView listViewStore = (ListView) storeview.findViewById(R.id.listViewStore);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//               getContext(),
//               android.R.layout.simple_list_item_1,
//               NAMES);

        Log.i("info StoreFragment","Start Listener");
        dbStoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot storeSnapshot : dataSnapshot.getChildren()){


                    Log.i("info StoreFragment","data snapshot storekey "+storeSnapshot.getKey());
                    Log.i("info StoreFragment","data snapshot value "+storeSnapshot.getValue());
                    Store oneStore = new Store();
                    String storeId = (String) storeSnapshot.child("storeID").getValue();
                    String storeName = (String) storeSnapshot.child("storeName").getValue();
                    oneStore.setStoreID(storeId);
                    oneStore.setStoreName(storeName);
                    storeLists.add(oneStore);

                    Log.i("info StoreFragment","Store Added" +storeId);

                }

                Log.i("info StoreFragment","Adding Store Done");
                CustomAdapter customAdapter = new CustomAdapter();
                listViewStore.setAdapter(customAdapter);
                listViewStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getContext(),"check = "+storeLists.get(i).getStoreID(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), TheStoreActivity.class);
                        intent.putExtra("storeID",storeLists.get(i).getStoreID());
                        intent.putExtra("storeName",storeLists.get(i).getStoreName());
                        startActivity(intent);
                    }
                });

//                if(dataSnapshot.exists()){
//
//                    Store store = dataSnapshot.getChildren();
//                    Log.i("info StoreFragment","Data Exists"+dataSnapshot);
//                    store.getITEMS().get(1).getItemId();
//                    storeLists.add(store);
//                    Log.i("info StoreFragment","Store Added");
//                } else {
//                    Log.i("info StoreFragment","dataSnapshot in StoreFragment invalid");
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




//        Button b = (Button) storeview.findViewById(R.id.clickStore);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AStoreActivity.class);
//                startActivity(intent);
//            }
//        });



        return storeview;
    }


    public class CustomAdapter extends BaseAdapter {

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
        public View getView(int i, View storelistview, ViewGroup viewGroup) {
            storelistview = getLayoutInflater().inflate(R.layout.custom_store_listview, null);
            Log.i("Info Adapter","INDEX OF i = "+i);
            Store theStore = storeLists.get(i);

            Log.i("Info Adapter","StoreINFO  " +theStore.getStoreID() + " " +theStore.getStoreName() );
            ImageView imageView = (ImageView) storelistview.findViewById(R.id.imageView);
            TextView textStoreName = (TextView) storelistview.findViewById(R.id.cusTextStoreName);
            TextView textStoreDesc = (TextView) storelistview.findViewById(R.id.cusTextStoreDesc);

            imageView.setImageResource(IMAGES[i]);
            textStoreName.setText(theStore.getStoreID());
            textStoreDesc.setText(theStore.getStoreName());
            return storelistview;



        }
    }

}
