package com.example.henry.cse477;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class mySettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        TextView five = (TextView) findViewById(R.id.TopLog);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        five.setTypeface(face);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_settings, menu);
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
        Intent myIntent = new Intent(mySettings.this, BAC.class);
        mySettings.this.startActivity(myIntent);
    }

    public void me(View view){
        Intent myIntent = new Intent(mySettings.this, Me.class);
        mySettings.this.startActivity(myIntent);
    }

    public void progressReport(View view){
        Intent myIntent = new Intent(mySettings.this, progress.class);
        mySettings.this.startActivity(myIntent);
    }

    public void setTime(View view){
        Intent myIntent = new Intent(mySettings.this, setTime.class);
        mySettings.this.startActivity(myIntent);
    }
    public void setUUID(View view){
        Intent myIntent = new Intent(mySettings.this, setUUID.class);
        mySettings.this.startActivity(myIntent);
    }

    public void setContact(View view){
        Intent myIntent = new Intent(mySettings.this,setContact.class);
        mySettings.this.startActivity(myIntent);
    }

    public void address(View view){
        Intent myIntent = new Intent(mySettings.this, myAddress.class);
        mySettings.this.startActivity(myIntent);
    }
}
