package com.karnjang.firebasedemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.models.User;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class UserInfoActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    TextView nfcStatus;
    TextView nfcMassage;
    String username;
    int userLevel;
    int userTotalXp;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");
    private ProgressBar mProgressBar;
    private int mProgressStatus = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcStatus = (TextView) findViewById(R.id.nfcStatus);
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        if (bundle != null) {

            dbUserRef.child(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        Log.i("UserInfoAct INFO","Check USER json "+dataSnapshot.getValue());
                        Toast.makeText(getApplicationContext(), "Setting XP", Toast.LENGTH_SHORT).show();
                        TextView userText = (TextView) findViewById(R.id.textUsername);
                        int userLevel = user.getTotalXp() / 100;
                        int userTotalXp = user.getTotalXp() % 100;
                        userText.setText("Username : " + user.getUsername() + "\n Total EXP : " + user.getTotalXp() + "\n User Level : " + userLevel);
                        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
                        mProgressBar.setProgress(userTotalXp);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error No Xp Value", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Error No Bundle From Main", Toast.LENGTH_SHORT).show();
        }
//         ######### Working Only On Real Device ########
//        Log.i("Info", "Set Textview nfcStatus");
//        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        Log.i("Info", "Check NFC Status");
//        if (nfcAdapter.isEnabled()) {
//            nfcStatus.setText("NFC is Enabled");
//        } else {
//            nfcStatus.setText("NFC is disabled.");
//        }


    }
}
