package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.datatypes.User;

public class BankingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private User u;

    public static BankingFragment newInstance(int sectionNumber) {
        BankingFragment fragment = new BankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public BankingFragment() {
        u = MainActivity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banking, container,
                false);

        return rootView;
    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
//                ARG_SECTION_NUMBER));
//    }
}