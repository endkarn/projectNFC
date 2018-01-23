package com.karnjang.firebasedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;


/**
 * Created by ssppy on 19-Nov-17.
 */

public class SplashScreenActivity extends Activity {
    Handler handler = new Handler();
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);


        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(SplashScreenActivity.this, LoginActivity.class);
                Log.i("Info", "Start SplashScreen in 3 2 1 . . .");
                startActivity(intent);
                finish();
                Log.i("Info", " SplashScreen DONE ");

            }
        };
    }

    public void onResume(){
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}
