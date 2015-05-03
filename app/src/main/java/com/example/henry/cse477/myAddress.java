package com.example.henry.cse477;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class myAddress extends Activity {

    public static final String PREFS_NAME = "Latitude";
    public static final String PREFS_NAME1 = "Longitude";
    private static final String TAG = "LocationAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_address, menu);
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
        int state = 0;
        EditText editText = (EditText) findViewById(R.id.editText);
        String address = editText.getText().toString();

        int latitude = (int) getLatitudeFromAddress(getApplicationContext(), address);
        int longitude = (int) getLongitudeFromAddress(getApplicationContext(), address);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences settings1 = getSharedPreferences(PREFS_NAME1, 0);
        Map<String,?> keys = settings.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()) {
            state++;
        }
        state++;
        String set = Integer.toString(state);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(set, latitude);
        // Commit the edits!
        editor.commit();
        SharedPreferences.Editor editor1 = settings1.edit();
        editor1.putInt(set, longitude);
        editor1.commit();
        Intent myIntent = new Intent(myAddress.this, mySettings.class);
        myAddress.this.startActivity(myIntent);
    }

    public double getLatitudeFromAddress(Context context, String addresses) {
        Geocoder get = new Geocoder(context);
        List<Address> address;
        double latitude = 0;
        try{
            address = get.getFromLocationName(addresses, 1);
            if(address.size() > 0) {
                latitude = address.get(0).getLatitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }
        return latitude;
    }

    public double getLongitudeFromAddress(Context context, String addresses) {
        Geocoder get = new Geocoder(context);
        List<Address> address;
        double longitude = 0;
        try{
            address = get.getFromLocationName(addresses, 1);
            if(address.size() > 0) {
                longitude = address.get(0).getLongitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }
        return longitude;
    }
}
