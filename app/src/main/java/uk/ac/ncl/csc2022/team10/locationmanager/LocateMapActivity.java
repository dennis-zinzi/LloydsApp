package uk.ac.ncl.csc2022.team10.locationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.maps.model.LatLng;

import uk.ac.ncl.csc2022.team10.lloydsapp.*;
import uk.ac.ncl.csc2022.team10.tabbedpageadapters.MapTabbedPageAdapter;


public class LocateMapActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private MapTabbedPageAdapter mAdapter;
    private String[] tabNames = { "Map", "List" };
    private ActionBar actionBar;

    LatLng userLocation;

    final String GOOGLE_KEY = "AIzaSyDL321g41odpLgnKf3CC61BIrobfAdjo2c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_map);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();

        mAdapter = new MapTabbedPageAdapter(getSupportFragmentManager());

        try {
            viewPager.setAdapter(mAdapter);
        }
        catch (NullPointerException e){
            if(viewPager==null){Log.i("ViewPager","NULL");}
            else if(mAdapter==null){Log.i("mAdapt","NULL");}
            else{Log.i("Boh","????");}
        }
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

        //Get current location on Map
        LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        //Get User's current location

        if (location != null) {
            userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            //If cannot find user location, set by default to Lloyds headquarters
            //userLocation = new LatLng(51.516272, -0.095594);

            //My uk location for test
            userLocation = new LatLng(54.979575,-1.585737);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locate_map, menu);
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
            return true;
        }
        else if(id == R.id.action_help){
            Intent i = new Intent(this,HelpActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_logout){
            setResult(2);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


}
