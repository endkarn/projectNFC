//package com.karnjang.firebasedemo;
//
//import android.app.Fragment;
//import android.content.Intent;
//import android.nfc.NfcAdapter;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.CallbackManager;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.karnjang.firebasedemo.models.User;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//
//    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference dbUserRef = dbref.child("users");
//    Map<String, String> values = new HashMap<>();
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        //getSupportActionBar().hide();
////        View decorView = getWindow().getDecorView();
////// Hide the status bar.
////        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
////        decorView.setSystemUiVisibility(uiOptions);
//    }
//
//    public void getStarted(View view){
//        EditText userName = (EditText) findViewById(R.id.userName);
//        EditText userPassword = (EditText) findViewById(R.id.userPassword);
//        final String yourName = userName.getText().toString();
//        String yourPass = userPassword.getText().toString();
//        final User user = new User(yourName,yourPass);
//
//        Log.i("Info", "Clicked");
//        // ######### Working Only On Real Device ########
//        Log.i("Info", " NFC Check ");
//        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if(nfcAdapter!=null && nfcAdapter.isEnabled()){
//            Toast.makeText(this, "NFC Available", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "NFC not available", Toast.LENGTH_SHORT).show();
//        }
//        Log.i("Info", "Listener Value");
//        dbUserRef.child(yourName).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    Toast.makeText(getApplicationContext(), "Loging In", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "Created New User", Toast.LENGTH_SHORT).show();
//                    setUser(user);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//        intent.putExtra("username",yourName);
//        Log.i("info", "put extra done");
//
//
//
//
//        startActivity(intent);
//        finish();
//
//        Log.i("info", "start userinfo");
//    }
//
//    public void setUser(User user){
//
//
//        Log.i("Info", "get user");
//
//        //values.put("userName", yourName);
//
//       // dbUserRef.push().setValue(user);
//        dbUserRef.child(user.getUsername()).setValue(user);
//
//        dbUserRef.push().setValue(values, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
//                if (databaseError == null) {
//
//                    Log.i("Info", "Save successful");
//
//                } else {
//
//                    Log.i("Info", "Save failed");
//
//                }
//
//            }
//        });
//    }
//}
