package uk.ac.ncl.csc2022.team10.locationmanager;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dennis on 12/3/15.
 */
public class Bank {
    private LatLng loc;
    private String name;
    private String openingHours;


    public Bank(LatLng latLng, String name){
        //this.loc = latLng;
        this.name = name;
    }

    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
