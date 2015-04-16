package uk.ac.ncl.csc2022.team10.lloydsapp;
/**
 * Created by Dennis.
 */
/*
    Modified by author: szholdiyarov
 */

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import uk.ac.ncl.csc2022.team10.banking.TopUpActivity;
import uk.ac.ncl.csc2022.team10.datatypes.User;

public class WalletFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private User user;
    private TextView amount1;
    private TextView amount2;
    private TextView walletName1;
    private TextView walletName2;
    private TextView nectarPoints;
    private TextView lloydsPoints;
    private Button but_topUp;
    private Button but_deposit;
    private Button but_redeem1;
    private Button but_redeem2;

    /*public static WalletFragment newInstance(int sectionNumber) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container,
                false);
        user = MainActivity.getUser();

        amount1 = (TextView) rootView.findViewById(R.id.textView3);
        amount2 = (TextView) rootView.findViewById(R.id.textView6);
        walletName1 = (TextView) rootView.findViewById(R.id.textView2);
        walletName2 = (TextView) rootView.findViewById(R.id.textView5);
        nectarPoints = (TextView) rootView.findViewById(R.id.textView_NectarPoints);
        lloydsPoints = (TextView) rootView.findViewById(R.id.textView_LloydsPoints);

        walletName1.setText(user.getWallets().get(0).getName());
        walletName2.setText(user.getWallets().get(1).getName());
        amount1.setText(String.format("%.2f", user.getWallets().get(0).getBalance()));
        amount2.setText(String.format("%.2f", user.getWallets().get(1).getBalance()));
        nectarPoints.setText(String.format("%.2f", user.getPoints().get(0).getPoints()));
        lloydsPoints.setText(String.format("%.2f", user.getPoints().get(1).getPoints()));

        but_topUp = (Button) rootView.findViewById(R.id.topUp_button);
        but_deposit = (Button) rootView.findViewById(R.id.button_deposit);

        but_redeem1 = (Button) rootView.findViewById(R.id.button_redeem1);
        but_redeem2 = (Button) rootView.findViewById(R.id.button_redeem2);

        but_topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopUpActivity.class);
                intent.putExtra("name", user.getWallets().get(0).getName());
                intent.putExtra("balance1", user.getWallets().get(0).getBalance());
                startActivity(intent);
            }
        });

        but_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopUpActivity.class);
                intent.putExtra("name", user.getWallets().get(1).getName());
                intent.putExtra("balance1", user.getWallets().get(1).getBalance());
                startActivity(intent);
            }
        });
        but_redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.redeemNectar();
                nectarPoints.setText(String.format("%.2f", user.getPoints().get(0).getPoints()));
                new AlertDialog.Builder(getActivity())
                        .setTitle("Well done")
                        .setMessage("Successfully redeemed")
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });
        but_redeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.redeemLloyds();
                lloydsPoints.setText(String.format("%.2f", user.getPoints().get(1).getPoints()));
                new AlertDialog.Builder(getActivity())
                        .setTitle("Well done")
                        .setMessage("Successfully redeemed")
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return rootView;
    }
}
