package uk.ac.ncl.csc2022.team10.locationmanager;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.ac.ncl.csc2022.team10.lloydsapp.*;

public class GoogleMapFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private GoogleMap googleMap;
    private SupportMapFragment fragment;
    private final Context context = getActivity();

    public static GoogleMapFragment newInstance(String param1, String param2) {
        GoogleMapFragment fragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_map, container,
                false);

        //KEEP TRYING SOMETHING!!!
        //googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleMap == null) {
            googleMap = fragment.getMap();
            googleMap.getUiSettings().setCompassEnabled(true);

            //To get traffic info
            //googleMap.setTrafficEnabled(true);


            //Get current location on Map
            LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = service.getBestProvider(criteria, false);
            Location location = service.getLastKnownLocation(provider);
            //Get User's current location
            LatLng userLocation;
            if (location != null) {
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            } else {
                //If cannot find user location, set by default to Lloyds headquarters
                //userLocation = new LatLng(51.516272, -0.095594);

                //My uk location for test
                userLocation = new LatLng(54.979575,-1.585737);
            }


            //Set zoom closer to user's location
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16));

            //Dot to represent current location
            //googleMap.setMyLocationEnabled(true);

            //Sets marker on Map to user's location
            MarkerOptions mo = new MarkerOptions();
            if(location != null) {
                googleMap.addMarker(mo.position(userLocation)).setTitle("Current Position");
            }
            else{
                //googleMap.addMarker(mo.position(userLocation).title("Lloyds Headquarters"));
                googleMap.addMarker(mo.position(userLocation).title("Current position"));
            }

        }
    }


}
