package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Me extends Activity {
    public static final String PREFS_NAME = "Contact Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String name = settings.getString("Name", null);
        TextView detail = (TextView) findViewById(R.id.textView5);
        detail.setText(name);
        TextView one = (TextView) findViewById(R.id.textView);
        TextView two = (TextView) findViewById(R.id.textView2);
        TextView three = (TextView) findViewById(R.id.textView3);
        TextView four = (TextView) findViewById(R.id.textView4);
        TextView five = (TextView) findViewById(R.id.TopLog);
        TextView six = (TextView) findViewById(R.id.button6);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        detail.setTypeface(face);
        one.setTypeface(face);
        two.setTypeface(face);
        three.setTypeface(face);
        four.setTypeface(face);
        five.setTypeface(face);
        six.setTypeface(face);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_me, menu);
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
        Intent myIntent = new Intent(Me.this, BAC.class);
        Me.this.startActivity(myIntent);
}

    public void progressReport(View view){
        Intent myIntent = new Intent(Me.this, progress.class);
        Me.this.startActivity(myIntent);
    }

    public void settings(View view){
        Intent myIntent = new Intent(Me.this, mySettings.class);
        Me.this.startActivity(myIntent);
    }
}
