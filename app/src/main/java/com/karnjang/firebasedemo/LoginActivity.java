package com.karnjang.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karnjang.firebasedemo.models.NewUser;
import com.karnjang.firebasedemo.models.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    User facebookUser = new User();
    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginButton loginButton;
    Button getStartButton;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbUserRef = dbref.child("users");
    Map<String, String> values = new HashMap<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStartButton = findViewById(R.id.button2);

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String accesstoken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getData(object);
                        Log.d("raw facebook response", response.toString());
                        Log.d("raw facebook object", object.toString());
                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("field", "id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            // facebookName.setText(AccessToken.getCurrentAccessToken().getUserId());

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    getData(object);
                    Log.d("raw facebook response", response.toString());
                    Log.d("raw facebook object", object.toString());

                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("field", "id,email,birthday,friends");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    public void getStarted(View view){
//        EditText userName = (EditText) findViewById(R.id.userName);
//        EditText userPassword = (EditText) findViewById(R.id.userPassword);
//
//        FOR TESTING ONLY
//        final String yourName = userName.getText().toString();
        final String yourName = facebookUser.getUsername();


//
//        String yourPass = userPassword.getText().toString();
//        final User user = new User(yourName,yourPass);


        Log.i("Info", "Listener Value");
        dbUserRef.child(facebookUser.username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "Loging In", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Created New User", Toast.LENGTH_SHORT).show();
                    setUser(facebookUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username",yourName);

         SharedPreferences userPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
         SharedPreferences.Editor userEditor = userPref.edit();
         userEditor.putString("SH_USERNAME",yourName);
         // Change Default Username
        //userEditor.putString("SH_USERNAME","KARNAWAT");
         userEditor.apply();

        Log.i("info", "put extra done");


        startActivity(intent);
        finish();

        Log.i("info", "start userinfo");
    }

    public void setUser(User user){
        Log.i("Info", "get user");
//        values.put("userName", user.getUsername());
//        dbUserRef.push().setValue(user);
        NewUser newUser = new NewUser(user.getUsername(),user.getUserid());
        dbUserRef.child(user.getUsername()).setValue(newUser);
//        dbUserRef.push().setValue("testuser", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError == null) {
//                    Log.i("Info", "Save successful");
//                } else {
//                    Log.i("Info", "Save failed");
//                }
//            }
//        });


    }


    private void getData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
            Log.d("profile_picture url", profile_picture.toString());
            getStartButton.setText(object.getString("name"));
            getStartButton.setVisibility(View.VISIBLE);
            Log.d("test",object.getString("name"));
            facebookUser.setUsername(object.getString("name"));
            facebookUser.setUserid(object.getString("id"));
            facebookUser.setPictureProfile(profile_picture.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
