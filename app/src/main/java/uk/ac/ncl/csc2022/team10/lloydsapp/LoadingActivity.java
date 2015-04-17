package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by: Dennis Zinzi
 */
public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Context context = this;

        //Amount of time loading screen will be shown
        final int SHOW_TIME = 3000;

		/*
		 * Showing loading screen with a timer
		 */
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Starts Login screen activity
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SHOW_TIME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}