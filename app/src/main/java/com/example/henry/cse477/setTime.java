package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class setTime extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        TextView five = (TextView) findViewById(R.id.TopLog);
        TextView two = (TextView) findViewById(R.id.textView6);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        five.setTypeface(face);
        two.setTypeface(face);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_time, menu);
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

    public void save(View view){
        Intent myIntent = new Intent(setTime.this, mySettings.class);
        setTime.this.startActivity(myIntent);
    }

    public void cancel(View view){
        Intent myIntent = new Intent(setTime.this, mySettings.class);
        setTime.this.startActivity(myIntent);
    }
}
