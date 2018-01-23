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

import com.karnjang.firebasedemo.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreItemListFragment extends Fragment {

    int[] IMAGES = {R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2, R.drawable.store_image1, R.drawable.store_image2};
    String[] NAMES = {"STORE00", "STORE01", "STORE02", "STORE03", "STORE04", "STORE05", "STORE06"};
    String[] PRICE = {"100", "150", "120", "133", "444", "555", "666"};

    public TheStoreItemListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theitemlistview = inflater.inflate(R.layout.fragment_the_store_item_list, container, false);
        ListView listViewTheStoreItemList = (ListView) theitemlistview.findViewById(R.id.listViewItemList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                NAMES);

       CustomAdapter customAdapter = new CustomAdapter();
       listViewTheStoreItemList.setAdapter(customAdapter);

        return theitemlistview;
    }

    public class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount()  {
            return IMAGES.length;
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
            Log.i("Info", "STORE VALUE NAME" + NAMES[i] + " DESC " + PRICE[i]);
            imageItemView.setImageResource(IMAGES[i]);
            textItemName.setText(NAMES[i]);
            textItemPrice.setText(PRICE[i]);


            return theitemlistview;
        }
    }

}
