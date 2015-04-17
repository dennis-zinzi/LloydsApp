package uk.ac.ncl.csc2022.team10.lloydsapp;

/**
  * Modified by author: Sanzhar Zholdiyarov
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.ac.ncl.csc2022.team10.banking.PayBills;
import uk.ac.ncl.csc2022.team10.banking.PayPersonActivity;
import uk.ac.ncl.csc2022.team10.banking.StandingOrders;
import uk.ac.ncl.csc2022.team10.banking.StatementActivity;
import uk.ac.ncl.csc2022.team10.datatypes.User;

public class BankingFragment extends Fragment {
    /*Declarations of variables */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button viewStatement;
    private Button payPerson;
    private Button payBills;
    private Button standingOrders;
    private Intent intent;

    /*public static BankingFragment newInstance(int sectionNumber) {
        BankingFragment fragment = new BankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banking, container,
                false);
        /* Set appropriate buttons */
        viewStatement = (Button) rootView.findViewById(R.id.topUp_button);
        payPerson = (Button) rootView.findViewById(R.id.payPerson);
        payBills = (Button) rootView.findViewById(R.id.payBills);
        standingOrders = (Button) rootView.findViewById(R.id.standingOrders);
        /* Set buttons listeners */
        viewStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), StatementActivity.class);
                startActivityForResult(intent,2);
            }
        });

        payPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PayPersonActivity.class);
                startActivityForResult(intent,2);
            }
        });

        payBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), PayBills.class);
                startActivityForResult(intent,2);
            }
        });
        standingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), StandingOrders.class);
                startActivityForResult(intent,2);
            }
        });
        return rootView;
    }

}