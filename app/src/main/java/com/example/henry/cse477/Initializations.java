package com.example.henry.cse477;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Initializations extends ActionBarActivity {

    public static final String PREFS_NAME = "Contact Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initializations);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initializations, menu);
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

    public void submit(View view){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String name = editText.getText().toString();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Name", name);
        editor.commit();
        Intent myIntent = new Intent(Initializations.this, BAC.class);
        Initializations.this.startActivity(myIntent);
    }
}
