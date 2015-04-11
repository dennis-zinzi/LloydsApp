package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.banking.PayPersonActivity;
import uk.ac.ncl.csc2022.team10.banking.StatementActivity;
import uk.ac.ncl.csc2022.team10.datatypes.User;

public class BankingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private User user;

    private Button viewStatement;
    private Button payPerson;
    private Button payBills;
    private Button standingOrders;

    public static BankingFragment newInstance(int sectionNumber) {
        BankingFragment fragment = new BankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public BankingFragment() {
        user = MainActivity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banking, container,
                false);

        viewStatement = (Button)rootView.findViewById(R.id.viewStatement);
        payPerson = (Button)rootView.findViewById(R.id.payPerson);

        viewStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StatementActivity.class);
                startActivity(intent);
            }
        });

        payPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayPersonActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
//                ARG_SECTION_NUMBER));
//    }
}