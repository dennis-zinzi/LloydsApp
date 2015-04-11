package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.ncl.csc2022.team10.datatypes.Account;
import uk.ac.ncl.csc2022.team10.datatypes.Contact;
import uk.ac.ncl.csc2022.team10.datatypes.User;


public class NewContactActivity extends ActionBarActivity {
    User user;
    Button create;
    EditText nameBox;
    EditText accountNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_contact);
        setTitle("New Contact");

        user = MainActivity.getUser();


        create = (Button)findViewById(R.id.create);
        nameBox = (EditText)findViewById(R.id.nameBox);
        accountNum = (EditText)findViewById(R.id.accountNum);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameBox.getText().toString().equals("") && !accountNum.getText().toString().equals("")){
                    //Check DB for Account with corresponding Account Number
                    //Account acc = SELECT * FROM Accounts WHERE AccountID = Integer.parseInt(accountNum.toString());
                    Account acc = new Account(Integer.parseInt(accountNum.getText().toString()),200,9000);
                    //Should throw Alert Dialog if no Account match
                    Contact newContact = new Contact(nameBox.getText().toString(),acc);
                    user.addContact(newContact);
                    TransferFragment.setToContact(newContact.getName());
                    TransferFragment.setSelectedContact(newContact);
                    finish();
                }
                else {
                    if (nameBox.getText().toString().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(NewContactActivity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Empty Name field");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(NewContactActivity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Empty Account Number field");
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_help) {
            Intent i = new Intent(this, HelpActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_logout) {
            // End MainActivity
            setResult(1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If requestCode of parent equals 3 and resultCode of child is 2, then
        // child requested to logout, thus MainActivity should finish
        if (requestCode == 3) {
            if (resultCode == 2) {
                setResult(1);
                finish();
            }
        }
    }
}