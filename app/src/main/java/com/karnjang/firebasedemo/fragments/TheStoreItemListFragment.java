package com.karnjang.firebasedemo.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.ExchangeItemActivity;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.models.Item;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreItemListFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");
    DatabaseReference dbUserRef = dbref.child("users");

    int[] IMAGES = {R.drawable.drink1,
            R.drawable.drink1,
            R.drawable.drink2,
            R.drawable.drink3};
    String[] NAMES = {"STORE00", "STORE01", "STORE02", "STORE03", "STORE04", "STORE05", "STORE06"};
    String[] PRICE = {"100", "150", "120", "133", "444", "555", "666"};
    ArrayList<Item> itemArrayList = new ArrayList<>();
    String storeId;
    Integer userPoint;
    String userName;



    public TheStoreItemListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theitemlistview = inflater.inflate(R.layout.fragment_the_store_item_list, container, false);
        final GridView listViewTheStoreItemList = (GridView) theitemlistview.findViewById(R.id.listViewItemList);
        storeId = getActivity().getIntent().getExtras().getString("storeID");
        Log.i("######StoreListItem","get store id"+storeId);

        SharedPreferences userPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = userPref.getString("SH_USERNAME","");




        dbStoreRef.child(storeId).child("ITEMS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    Log.i("######StoreListItem","Loop For");
                    Item item = itemSnapshot.getValue(Item.class);
                    Log.i("######StoreListItem","name "+item.getItemName()+" price "+item.getItemPrice());
                    itemArrayList.add(item);
                }

                dbUserRef.child(userName).child("totalPoints").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("Check User", "User Datasnap ==  " + dataSnapshot.getValue());
                        Long longuserPoint = (Long) dataSnapshot.getValue();
                        userPoint = longuserPoint.intValue();

                        CustomItemListAdapter customItemListStore = new CustomItemListAdapter();
                        listViewTheStoreItemList.setAdapter(customItemListStore);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });







//                listViewTheStoreItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(getActivity(), ExchangeItemActivity.class);
//
//                        Log.i("Info Adapter","The Store Itemlist  " +storeId + " " +itemArrayList.get(i).getItemId() );
//                        intent.putExtra("StoreID", storeId);
//                        intent.putExtra("ItemID", itemArrayList.get(i).getItemId());
//
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return theitemlistview;
    }


    public class CustomItemListAdapter extends BaseAdapter {
        @Override
        public int getCount()  {
            return itemArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(int i, View theitemlistview, ViewGroup viewGroup) {
            theitemlistview = getLayoutInflater().inflate(R.layout.custom_the_store_item_list, null);
            ImageView imageItemView = (ImageView) theitemlistview.findViewById(R.id.imageItemView);
            TextView textItemName = (TextView) theitemlistview.findViewById(R.id.textItemName);
            TextView textItemPrice = (TextView) theitemlistview.findViewById(R.id.textItemPrice);
            TextView textItemAmount = (TextView) theitemlistview.findViewById(R.id.textItemAmount);
            Button button = (Button) theitemlistview.findViewById(R.id.buttonItem) ;

            final Item item = itemArrayList.get(i);


            Log.i("#StoreListItem GetView","name "+item.getItemName()+" price "+item.getItemPrice());
            imageItemView.setImageResource(IMAGES[item.getItemType()]);
            textItemName.setText(item.getItemName());
            textItemPrice.setText(""+item.getItemPrice());

            textItemAmount.setText("Amount:"+item.getItemAmount());

Log.i("CHeck user", "User POINT =="+userPoint);

            if(item.getItemAmount() > 0 && userPoint >= item.getItemPrice()){
                button.setText("USE"+item.getItemPrice());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent itemIntent = new Intent(getActivity(), ExchangeItemActivity.class);
                        itemIntent.putExtra("StoreID", storeId);
                        itemIntent.putExtra("ItemID", item.getItemId());

                        startActivity(itemIntent);

                    }
                });
            }else {
                button.setText("USE"+item.getItemPrice());
                button.setEnabled(false);
            }


            return theitemlistview;
        }
    }


}
