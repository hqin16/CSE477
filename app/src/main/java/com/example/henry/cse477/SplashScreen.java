package com.example.henry.cse477;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


public class SplashScreen extends Activity {

    public static final String PREFS_NAME = "SetUp";
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView test = (TextView) findViewById(R.id.TopLog);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        test.setTypeface(face);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String value = settings.getString("key", null);
                SharedPreferences.Editor editor = settings.edit();
                if(value == null){
                    editor.putString("key", "ok");
                    editor.commit();
                    startActivity(new Intent(SplashScreen.this, Initializations.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, BAC.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH * 1000);

    }


}
