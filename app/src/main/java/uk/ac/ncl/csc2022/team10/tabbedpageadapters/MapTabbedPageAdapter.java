package uk.ac.ncl.csc2022.team10.tabbedpageadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uk.ac.ncl.csc2022.team10.locationmanager.*;

/**
 * Created by Dennis Zinzi on 10/3/15.
 */
public class MapTabbedPageAdapter extends FragmentPagerAdapter{

    public MapTabbedPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int index){

        switch (index){

            case 0:
                //GoogleMap fragment
                return new GoogleMapFragment();
            case 1:
                return new BankListFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
