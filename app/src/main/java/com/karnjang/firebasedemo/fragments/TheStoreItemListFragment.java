package com.karnjang.firebasedemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.karnjang.firebasedemo.models.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreItemListFragment extends Fragment {

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");

    int[] IMAGES = {R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2};
    String[] NAMES = {"STORE00", "STORE01", "STORE02", "STORE03", "STORE04", "STORE05", "STORE06"};
    String[] PRICE = {"100", "150", "120", "133", "444", "555", "666"};
    ArrayList<Item> itemArrayList = new ArrayList<>();

    public TheStoreItemListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theitemlistview = inflater.inflate(R.layout.fragment_the_store_item_list, container, false);
        final ListView listViewTheStoreItemList = (ListView) theitemlistview.findViewById(R.id.listViewItemList);

        String storeId = getActivity().getIntent().getExtras().getString("storeID");
        Log.i("######StoreListItem","get store id"+storeId);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
////                getContext(),
////                android.R.layout.simple_list_item_1,
////                NAMES);
        dbStoreRef.child(storeId).child("ITEMS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    Log.i("######StoreListItem","Loop For");
                    Item item = itemSnapshot.getValue(Item.class);
                    Log.i("######StoreListItem","name "+item.getItemName()+" price "+item.getItemPrice());
                    itemArrayList.add(item);
                }

                CustomItemListAdapter customItemListStore = new CustomItemListAdapter();
                listViewTheStoreItemList.setAdapter(customItemListStore);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return theitemlistview;
    }

    public class CustomItemListAdapter extends BaseAdapter{
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
            Item item = itemArrayList.get(i);
            Log.i("#StoreListItem GetView","name "+item.getItemName()+" price "+item.getItemPrice());
            imageItemView.setImageResource(IMAGES[item.getItemType()]);
            textItemName.setText(item.getItemName());
            textItemPrice.setText(""+item.getItemPrice());


            return theitemlistview;
        }
    }

}
