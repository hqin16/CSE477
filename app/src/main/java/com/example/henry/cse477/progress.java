package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class progress extends Activity {
    public static final String PREFS_NAME = "Contact Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String name = settings.getString("BAC", null);
        double temp = Double.parseDouble(name);
        double BAC = 0;
        double voltage = (temp *5/1023);
        if(voltage < 2.2){
            BAC = (.0091)*voltage;
        }else if( voltage < 3.9) {
            BAC = .0207 * Math.pow(voltage, 2) - .0886*voltage + .1164;
        } else{
            BAC = .3527*voltage - 1.289;
        }
        BAC = Math.round(BAC * 1000.0) / 1000.0;
        TextView detail = (TextView) findViewById(R.id.textView5);
        detail.setText("" + BAC);
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
        startActivity(callIntent);
    }
}
