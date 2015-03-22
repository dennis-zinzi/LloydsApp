package uk.ac.ncl.csc2022.team10.lloydsapp;

/**
 * Created by Dennis on 7/3/15.
 */

import uk.ac.ncl.csc2022.team10.datatypes.*;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TextView;
import android.widget.TextView;

public class TransferFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView balanceLabel;
    private Account acc;

    public static TransferFragment newInstance(int sectionNumber) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public TransferFragment() {
        acc = new Account(25,1096.67,2500.00);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfer, container,
                false);

        balanceLabel = (TextView)rootView.findViewById(R.id.availBalance);
        balanceLabel.setText(acc.getBalance()+"");

        return rootView;
    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
//                ARG_SECTION_NUMBER));
//    }
}
