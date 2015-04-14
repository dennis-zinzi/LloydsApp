package uk.ac.ncl.csc2022.team10.lloydsapp;

//import android.os.Build;
import java.io.IOException;

//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import uk.ac.ncl.csc2022.team10.datatypes.*;
import uk.ac.ncl.csc2022.team10.notificationmanager.*;
import uk.ac.ncl.csc2022.team10.tabbedpageadapters.TabbedPageAdapter;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabbedPageAdapter mAdapter;
    private ActionBar actionBar;
    // Tab names
    private String[] tabNames = { "Balance", "Transfer", "Wallets", "Banking" };

    private static User user;
    /*Notifications*/
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "752723874311";

    public void getRegId() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                InsertId ins = new InsertId();
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    try {
                        ins.sendPost(regid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }
        }.execute(null, null, null);
    }

	/* End of notifications */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        setTitle("LLoyds Student");
        getRegId(); // On every start register this device
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        //makeUserExample();

        mAdapter = new TabbedPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tabName : tabNames) {
            actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_help) {
            Intent i = new Intent(this, HelpActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_logout) {
            // End MainActivity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If requestCode of parent equals 2 and resultCode of child is 1, then
        // child requested to logout, thus MainActivity should finish
        if (requestCode == 2) {
            if (resultCode == 1) {
                finish();
            }
        }
    }

    /**
     *  Example User until implemented with Database
     */
     public void makeUserExample(){
        user = new User("Dennis", "123456");
        Account a = new Account(1,100,1000);
        user.addAccount(a);
        user.addContact(new Contact("Tom",new Account(2,50,1000)));
        user.addContact(new Contact("Sanzhar",new Account(3,190,5000)));
        user.addContact(new Contact("Rhys",new Account(4,500,10000)));
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public static User getUser(){
        return user;
    }

    public static void setUser(User u){
        user = u;
    }
}