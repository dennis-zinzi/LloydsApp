package uk.ac.ncl.csc2022.team10.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.*;

import uk.ac.ncl.csc2022.team10.datatypes.Contact;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;

/**
 * Created by Dennis on 7/4/15.
 */
public class ContactDialog extends DialogFragment {

    List<Contact> contactList;
    Contact selected;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //contactList = new ArrayList<Contact>();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Contact")
                .setSingleChoiceItems((android.widget.ListAdapter) contactList,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = contactList.get(which);
                    }
                })
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(),"HELLO",Toast.LENGTH_LONG);
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
}
