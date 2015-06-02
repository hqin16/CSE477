package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class setUUID extends Activity {

    public static final String PREFS_NAME = "Contact Info";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_uuid);
        TextView five = (TextView) findViewById(R.id.TopLog);
        TextView two = (TextView) findViewById(R.id.textView6);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        five.setTypeface(face);
        two.setTypeface(face);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_uuid, menu);
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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        EditText contact = (EditText) findViewById(R.id.editText6);
        String UUID = contact.getText().toString().trim();
        Intent myIntent = new Intent(setUUID.this, mySettings.class);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("UUID", UUID);
        editor.commit();
        setUUID.this.startActivity(myIntent);
    }
    public void cancel(View view) {
        Intent myIntent = new Intent(setUUID.this, mySettings.class);
        setUUID.this.startActivity(myIntent);
    }
}
