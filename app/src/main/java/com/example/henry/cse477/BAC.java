package com.example.henry.cse477;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class BAC extends Activity implements LocationListener {

    public static final String PREFS_NAME = "Latitude";
    public static final String PREFS_NAME1 = "Longitude";

    private static final String TAG = "LocationAddress";
    public final static String EXTRA_MESSAGE = "com.example.henry.MESSAGE";
    private LocationManager locationManager;
    private String provider;
    private double lat;
    private double lng;

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private InputStream inputStream = null;

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Insert your server's MAC address
    private static String address = "00:06:66:6D:96:96";

    private void CheckBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on


            if (btAdapter.isEnabled()) {

            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

    }




    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences settings1 = getSharedPreferences(PREFS_NAME1, 0);
            boolean check = false;
            String lattate = "lat";
            String longState = "long";
            Map<String,?> keys = settings.getAll();
            for(Map.Entry<String,?> entry : keys.entrySet()) {
                String temp = entry.getValue().toString();
                double latitude = Double.parseDouble(temp);
                if (lat < latitude) {
                    check = true;
                }
            }

            Map<String,?> keys1 = settings1.getAll();
            for(Map.Entry<String,?> entry : keys1.entrySet()) {
                String temp = entry.getValue().toString();
                double longitude = Double.parseDouble(temp);
                if (lng < longitude && check) {
                    createNotification();
                }
            }

            timerHandler.postDelayed(this, 10000);
        }
    };

    Handler bluetoothHandler = new Handler();
    Runnable bluetoothRunnable = new Runnable() {

        @Override
        public void run() {
            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            // Two things are needed to make a connection:
            //   A MAC address, which we got above.
            //   A Service ID or UUID.  In this case we are using the
            //     UUID for SPP.
            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Unable connect", e);
            }
            btAdapter.cancelDiscovery();
            // Establish the connection.  This will block until it connects.
            try {
                btSocket.connect();

            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "Socket Closed", e);
                }
            }

            try {
                inputStream = btSocket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Failed", e);
            }
            int temp;
                try {
                  temp = inputStream.read();
                    AlertBox("Message", "" + (char) temp);
                } catch (IOException e) {
                    Log.e(TAG, "Failed", e);
                }

            bluetoothHandler.postDelayed(this, 10000);
        }
    };


    public void AlertBox( String title, String message ){
        new AlertDialog.Builder(this)
                .setTitle( title )
                .setMessage( message + " Press OK to exit." )
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        CheckBTState();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null) {
            onLocationChanged(location);
        }
        timerHandler.postDelayed(timerRunnable, 0);
        setContentView(R.layout.activity_bac);
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

    public void createNotification() {
       // Prepare intent which is triggered if the
       // notification is selected
       Intent intent = new Intent(this, LocationDisplay.class);
       PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
       // Build notification
       // Actions are just fake
       Notification noti = new Notification.Builder(this)
               .setContentTitle("ALERT")
               .setContentText("TIME TO TEST").setSmallIcon(R.drawable.uwlogo)
               .setContentIntent(pIntent).build();

       NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       // hide the notification after its selected
       noti.vibrate = new long[]{100, 200, 100, 500};
       noti.flags |= Notification.FLAG_AUTO_CANCEL;

       notificationManager.notify(0, noti);



   }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (location.getLatitude());
        lng = (location.getLongitude());
        //String address = getAddressFromLocation(location.getLatitude(), location.getLongitude(), getApplicationContext());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    public void Located(View view){
        Intent myIntent = new Intent(BAC.this, LocationDisplay.class);
        BAC.this.startActivity(myIntent);
    }
    public void progressReport(View view){
        Intent myIntent = new Intent(BAC.this, progress.class);
        BAC.this.startActivity(myIntent);
    }

    public void me(View view){
        Intent myIntent = new Intent(BAC.this, Me.class);
        BAC.this.startActivity(myIntent);
    }

    public void settings(View view){
        Intent myIntent = new Intent(BAC.this, mySettings.class);
        BAC.this.startActivity(myIntent);
    }

    public void bluetoothClick(View view){
        bluetoothHandler.postDelayed(bluetoothRunnable, 0);
    }

}
