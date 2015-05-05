package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class progress extends Activity {
    public static final String PREFS_NAME = "Contact Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connect(View view){
        Intent myIntent = new Intent(progress.this, BAC.class);
        progress.this.startActivity(myIntent);
    }

    public void me(View view){
        Intent myIntent = new Intent(progress.this, Me.class);
        progress.this.startActivity(myIntent);
    }

    public void settings(View view){
        Intent myIntent = new Intent(progress.this, mySettings.class);
        progress.this.startActivity(myIntent);
    }

    public void Call(View view){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String tel = settings.getString("Contact", null);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tel));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }
}
