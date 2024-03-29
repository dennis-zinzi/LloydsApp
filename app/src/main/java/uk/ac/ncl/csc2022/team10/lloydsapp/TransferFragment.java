package uk.ac.ncl.csc2022.team10.lloydsapp;

/**
 * Created by Dennis Zinzi on 7/3/15.
 */

import uk.ac.ncl.csc2022.team10.datatypes.*;
import uk.ac.ncl.csc2022.team10.dialogs.ContactDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TransferFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView balanceLabel;
    private Account acc;
    private ImageButton addContact;
    private User user;
    private static EditText toContact;
    private Button sendMoney;
    private EditText amountBox;
    private static Contact selectedContact;

    public static TransferFragment newInstance(int sectionNumber) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TransferFragment() {
        //acc = new Account(25,1096.67,2500.00);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfer, container,
                false);

        user = MainActivity.getUser();

        balanceLabel = (TextView)rootView.findViewById(R.id.availBalance);
        balanceLabel.setText(String.format("%.2f",user.getAccount().getBalance()));
        amountBox = (EditText)rootView.findViewById(R.id.amountBox);
        toContact = (EditText)rootView.findViewById(R.id.toContact);
        addContact = (ImageButton)rootView.findViewById(R.id.addContact);

        addContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showContactDialog();

            }
        });

        sendMoney = (Button)rootView.findViewById(R.id.sendMoney);
        sendMoney.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(!amountBox.getText().toString().equals("") && selectedContact != null) {
                    double amount = Double.parseDouble(String.format("%.2f",Double.parseDouble(amountBox.getText().toString())));
                    //Check if enough funds to make transfer
                    if((user.getAccount().getBalance()-amount)>=0) {
                        //Transfers inputted money to selected account
                        user.getAccount().transferFund(amount, selectedContact.getAccount());
                        Log.i("TRANSFERED", Double.parseDouble(amountBox.getText().toString()) + "to " + selectedContact.getName());
                        //Update balance label
                        balanceLabel.setText(String.format("%.2f",user.getAccount().getBalance()));
                    }
                    else{
                        if(Math.abs(user.getAccount().getBalance()-amount)<=user.getAccount().getOverdraftLimit()) {
                            user.getAccount().transferFund(amount, selectedContact.getAccount());
                            balanceLabel.setText(String.format("%.2f", user.getAccount().getBalance()));
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Not enough funds to make transfer");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                    //Reset fields
                    toContact.setText("");
                    selectedContact = null;
                    amountBox.setText("");
                }
                else{
                    if(amountBox.getText().toString().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Empty money field");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("No contact selected");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });


        return rootView;
    }


    private void showContactDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ContactDialog contactDialog= new ContactDialog();
        contactDialog.show(fm, "fragment_edit_name");
    }

    public static void setToContact(String contactName){
        toContact.setText(contactName);
    }

    public static void setSelectedContact(Contact c){
        selectedContact = c;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If requestCode of parent equals 2 and resultCode of child is 1, then
        // child requested to logout, thus MainActivity should finish
        if (requestCode == 3) {
            if (resultCode == 2) {
                getActivity().setResult(1);
                getActivity().finish();
            }
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
//                ARG_SECTION_NUMBER));
//    }
}
