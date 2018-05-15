package com.karnjang.firebasedemo.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karnjang.firebasedemo.R;
import com.karnjang.firebasedemo.models.Store;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheStoreDetailFragment extends Fragment {

    StorageReference mStorageRef  = FirebaseStorage.getInstance().getReference();
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStoreRef = dbref.child("STORE");
    int[] IMAGES = {R.drawable.store01, R.drawable.store02, R.drawable.store03, R.drawable.store04, R.drawable.store_image1, R.drawable.store_image2};


    public TheStoreDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theStoreDetailView = inflater.inflate(R.layout.fragment_the_store_detail,container,false);
        final TextView textTheStoreId = (TextView) theStoreDetailView.findViewById(R.id.textTheStoreId);
        final TextView textTheStoreName = (TextView) theStoreDetailView.findViewById(R.id.textTheStoreName);
        final ImageView imageStore = (ImageView) theStoreDetailView.findViewById(R.id.imageTheStore);
        final TextView textTheStoreDesc = (TextView) theStoreDetailView.findViewById(R.id.textStoreDesc);

        Log.i("TheStoreDetail INFO","Start Getting Data From The Store From Firebase" +dbStoreRef);

       String storeId = getActivity().getIntent().getExtras().getString("storeID");
       String index = storeId.substring(storeId.length() - 1);
       final Integer sIndex = Integer.parseInt(index);



        Log.i("TheStoreDetail INFO","Check StoreID From Activity "+storeId);
        dbStoreRef.child(storeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Store oneStore = new Store();
                    String storeId = (String) dataSnapshot.child("storeID").getValue();
                    String storeName = (String) dataSnapshot.child("storeName").getValue();
                    String storeDesc = (String) dataSnapshot.child("storeDesc").getValue();
                    String storeType = (String) dataSnapshot.child("storeType").getValue();
                    String storeLocation = (String) dataSnapshot.child("storeLocation").getValue();
                    oneStore.setStoreID(storeId);
                    oneStore.setStoreName(storeName);
                    oneStore.setStoreDesc(storeDesc);
                    oneStore.setStoreType(storeType);
                    oneStore.setStoreLocation(storeLocation);
                    Log.i("TheStoreDetail INFO","Check DATA StoreID"+storeId);
                    Log.i("TheStoreDetail INFO","Check DATA StoreName"+storeName);

                    final StorageReference imgUrl = mStorageRef.child("store/"+oneStore.getStoreID());

                    imgUrl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.i("test URL Image", "URL :: "+uri);
                            Picasso.with(getContext()).load(uri).fit().into(imageStore);
                        }

                    });

                    String s = "#"+storeType+" , @"+storeLocation ;

                    textTheStoreId.setText(storeName);
                    textTheStoreName.setText(s);
                    textTheStoreDesc.setText(Html.fromHtml(storeDesc));
                }else {
                    Log.i("TheStoreDetail INFO","No Data From Firebase");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.i("StoreDetail INFO","position"+storeId);


        return theStoreDetailView;
    }

}
