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
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    public static final String PREFS_NAME2 = "Contact Info";

    private static final String TAG = "LocationAddress";
    public final static String EXTRA_MESSAGE = "com.example.henry.MESSAGE";
    private LocationManager locationManager;
    private String provider;
    private double lat;
    private double lng;

    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;


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




    public void AlertBox( String title, String message ){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    Intent myIntent = new Intent(BAC.this, BAC.class);
                    BAC.this.

                    startActivity(myIntent);
                }
    }).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bac);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME2, 0);
        address = settings.getString("UUID", "00:06:66:6D:96:96");;
        TextView TopLogo=(TextView)findViewById(R.id.TopLog);
        Typeface face = Typeface.createFromAsset(getAssets(),"CP2.otf");
        TopLogo.setTypeface(face);
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
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       // Build notification
       // Actions are just fake
       Notification noti = new Notification.Builder(this)
               .setContentTitle("ALERT")
               .setContentText("TIME TO TEST").setSmallIcon(R.drawable.uwlogo)
               .setSound(sound)
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


    }

    @Override
    public void onProviderDisabled(String provider) {

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

    public void bluetoothConnect(View view){
        try{
            openBT();
        }
        catch(IOException e){AlertBox("ERROR", "Please Try Again");}
    }

    public void openBT() throws IOException
    {
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        btSocket.connect();

        inputStream = btSocket.getInputStream();

        beginListenForData();
    }

    public void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 35; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                int count = 0;
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter) {
                                    if(count == 1){
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable()
                                        {
                                            public void run()
                                            {
                                                try {
                                                    stopWorker = true;
                                                    inputStream.close();
                                                    btSocket.close();
                                                } catch (IOException ex){
                                                }
                                                SharedPreferences settings = getSharedPreferences(PREFS_NAME2, 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("BAC", data);
                                                editor.commit();
                                                AlertBox("ALERT", "PLEASE TURN OFF DEVICE");
                                            }
                                        });
                                    }
                                    count++;
                                }
                                else if(count > 0)
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

}
