package uk.ac.ncl.csc2022.team10.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import java.util.*;

import uk.ac.ncl.csc2022.team10.datatypes.*;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.NewContactActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.TransferFragment;

/**
 * Created by Dennis on 7/4/15.
 */
public class ContactDialog extends DialogFragment {

    private User user;
    private List<Contact> contactList;
    private static Contact selected;

    public ContactDialog(){
    }

    public void makeContacts(){
        contactList = new ArrayList<Contact>();
//        Contact c1 = new Contact("Dennis", new Account(1, 10, 100));
//        Contact c2 = new Contact("Tom", new Account(2,20,1000));
//        Contact c3 = new Contact("Sanzhar", new Account(3,25,500));
//        contactList.add(c1);
//        contactList.add(c2);
//        contactList.add(c3);
//
        for(Contact c : user.getContacts()){
            contactList.add(c);
        }

    }

    public List<String> getContactNames(List<Contact> contactList){
        List<String> names = new ArrayList<String>();
        for(Contact c : contactList){
            names.add(c.getName());
        }
        names.add("Add New Contact...");
        return names;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        user = MainActivity.getUser();
        makeContacts();
        final List<String> contactNames = getContactNames(contactList);
        builder.setTitle("Select Contact")
                .setSingleChoiceItems(contactNames.toArray(new String[contactNames.size()]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("SELECTED",which+"");
                        if(which == contactList.size()){
                            //Go to screen to add new Contact
                            Intent i = new Intent(getActivity(), NewContactActivity.class);
                            startActivity(i);
                            dismiss();
                        }
                        else {
                            selected = contactList.get(which);
                        }

                    }
                })
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Sets contact in TransferFragment to selected Contact from Dialog
                        TransferFragment.setToContact(selected.getName());
                        TransferFragment.setSelectedContact(selected);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static Contact getLastSelected(){
        if(selected != null) {
            return selected;
        }
        else{
            return new Contact("Empty",new Account(0,0.1,1));
        }
    }
}
