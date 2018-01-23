package com.karnjang.firebasedemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karnjang.firebasedemo.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreDetailFragment extends Fragment {


    public TheStoreDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thestoreview = inflater.inflate(R.layout.fragment_the_store_detail,container,false);
        TextView textTheStoreId = (TextView) thestoreview.findViewById(R.id.textTheStoreId);
        TextView textTheStoreName = (TextView) thestoreview.findViewById(R.id.textTheStoreName);

        int storePosition = getActivity().getIntent().getExtras().getInt("storePosition");

        textTheStoreId.setText("STORE_ID__"+Integer.toString(storePosition));
        Log.i("StoreDetail INFO","position"+storePosition);


        return thestoreview;
    }

}
