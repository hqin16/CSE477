package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;




public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreen.this, BAC.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH * 1000);
    }
}
