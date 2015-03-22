package uk.ac.ncl.csc2022.team10.locationmanager;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.*;
import uk.ac.ncl.csc2022.team10.lloydsapp.*;


public class BankListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    // the google key
    final String GOOGLE_KEY = "AIzaSyDL321g41odpLgnKf3CC61BIrobfAdjo2c";
    private double latitude;
    private double longitude;

    private ListView bankList;
    private List<String> banks;
    private ArrayAdapter<String> adaptBanks;



    public static BankListFragment newInstance(String param1, String param2) {
        BankListFragment fragment = new BankListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BankListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bank_list, container, false);

        //Get current location on Map
        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        //Get User's current location
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else {
            //If cannot find user location, set by default to Lloyds headquarters
            latitude = 51.516272;
            longitude = -0.095594;
        }

        Log.i("LAT",latitude+"");
        Log.i("LONG", longitude+"");

        Context context = getActivity().getApplicationContext();

        bankList = (ListView) rootView.findViewById(R.id.bankList);
        banks = new ArrayList<String>();
        adaptBanks = new ArrayAdapter<String>(context, R.layout.listrows, banks);

        bankList.setAdapter(adaptBanks);

        banks.add("TSB Heaton");
        adaptBanks.notifyDataSetChanged();

        return rootView;
    }

}
