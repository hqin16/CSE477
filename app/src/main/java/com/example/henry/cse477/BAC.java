package com.example.henry.cse477;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class BAC extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.henry.MESSAGE";

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            createNotification();
            timerHandler.postDelayed(this, 10000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timerHandler.postDelayed(timerRunnable, 0);
        setContentView(R.layout.activity_bac);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bac, menu);
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

   /*
    public void sendMessage(View view) {
      Intent intent = new Intent(this, DisplayMessageActivity.class);
      EditText editText = (EditText) findViewById(R.id.edit_message);
      String message = editText.getText().toString();
      intent.putExtra(EXTRA_MESSAGE, message);
      startActivity(intent);
    }
    */public void createNotification() {
       // Prepare intent which is triggered if the
       // notification is selected
       Intent intent = new Intent(this, LocationDisplay.class);
       PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
       // Build notification
       // Actions are just fake
       Notification noti = new Notification.Builder(this)
               .setContentTitle("New mail from " + "test@gmail.com")
               .setContentText("Subject").setSmallIcon(R.drawable.uwlogo)
               .setContentIntent(pIntent).build();

       NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       // hide the notification after its selected
       noti.vibrate = new long[]{100, 200, 100, 500};
       noti.flags |= Notification.FLAG_AUTO_CANCEL;

       notificationManager.notify(0, noti);



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



}
