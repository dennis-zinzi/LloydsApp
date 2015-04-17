package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import uk.ac.ncl.csc2022.team10.help.ContactUsActivity;
import uk.ac.ncl.csc2022.team10.help.FAQActivity;
import uk.ac.ncl.csc2022.team10.help.ProductInfoActivity;
import uk.ac.ncl.csc2022.team10.locationmanager.LocateMapActivity;

public class HelpActivity extends ActionBarActivity implements OnClickListener {

    Button nearestBranch;
    Button productInfo;
    Button faq;
    Button contactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_help);
        setTitle("Help");
        addListenerOnButton();
    }


    public void addListenerOnButton(){
        final Context context = this;

        //Determine button to add listener to
        nearestBranch = (Button) findViewById(R.id.button_redeem1);
        nearestBranch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Opens Google Map
                Intent intent = new Intent(context, LocateMapActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 3);
            }
        });

        productInfo = (Button) findViewById(R.id.productInfo);
        productInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Goes to product info Page
                //Intent intent = new Intent(context, Object.class);
                //startActivityForResult(intent, 3);
            }
        });

        faq = (Button)findViewById(R.id.topUp_button);
        faq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goes to FAQ page
                Intent intent = new Intent(context, FAQActivity.class);
                startActivityForResult(intent,3);
            }
        });

        contactUs = (Button)findViewById(R.id.button_deposit);
        contactUs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUsActivity.class);
                startActivityForResult(intent,3);
            }
        });

        productInfo = (Button)findViewById(R.id.productInfo);
        productInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductInfoActivity.class);
                startActivityForResult(intent,3);
            }
        });

        new AsyncCaller().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        else if(id == R.id.action_logout){
            setResult(1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If requestCode of parent equals 2 and resultCode of child is 1, then
        // child requested to logout, thus MainActivity should finish
        if (requestCode == 3) {
            if (resultCode == 2) {
                setResult(1);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.i("USER", "Something happened");
        MainActivity.getTimeCounter().resetTimer();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... params) {
            //If user idle for 60 seconds log him out
            while(MainActivity.getTimeCounter().countTime()<60000){}
            setResult(1);
            finish();

            return null;
        }

    }
}