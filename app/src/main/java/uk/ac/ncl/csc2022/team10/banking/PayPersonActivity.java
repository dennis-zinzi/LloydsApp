package uk.ac.ncl.csc2022.team10.banking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.datatypes.*;
import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;


/**
 * Created by: Dennis Zinzi
 */
public class PayPersonActivity extends ActionBarActivity {

    User user;
    Button confirmButton;
    Button cancelButton;
    EditText amountPerson;
    EditText toAccountNum;
    EditText contactNameSave;
    TextView availNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_pay_person);
        user = MainActivity.getUser();

        setTitle("Pay Person");

        amountPerson = (EditText)findViewById(R.id.amountPerson);
        toAccountNum = (EditText)findViewById(R.id.toAccountNum);
        contactNameSave = (EditText)findViewById(R.id.contactNameSave);

        availNum = (TextView)findViewById(R.id.availNum);
        availNum.setText(String.format("%.2f",user.getAccount().getBalance()));

        confirmButton = (Button)findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountPerson.getText().toString().equals("") && !toAccountNum.getText().toString().equals("")){
                    if(contactNameSave.getText().toString().equals("") || contactNameSave.getText() == null){
                        //Prompt User if sure to continue without saving contact
                        AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Contact will NOT be saved, Continue?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(user.getAccount().getBalance()-Double.parseDouble(amountPerson.getText().toString())>=0) {
                                            Account acc = new Account(Integer.parseInt(toAccountNum.getText().toString()), 500, 10000);
                                            user.getAccount().transferFund(Double.parseDouble(String.format("%.2f", Double.parseDouble(amountPerson.getText().toString()))), acc);
                                            amountPerson.setText("");
                                            toAccountNum.setText("");
                                            contactNameSave.setText("");
                                            availNum.setText(String.format("%.2f",user.getAccount().getBalance()));
                                        }
                                        else{
                                            if(Math.abs(user.getAccount().getBalance()-Double.parseDouble(amountPerson.getText().toString()))<=user.getAccount().getOverdraftLimit()) {
                                                AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
                                                alertDialog.setTitle("Warning");
                                                alertDialog.setMessage("You will now enter in your Overdraft, Continue?");
                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Account acc = new Account(Integer.parseInt(toAccountNum.getText().toString()), 500, 10000);
                                                                user.getAccount().transferFund(Double.parseDouble(String.format("%.2f", Double.parseDouble(amountPerson.getText().toString()))), acc);
                                                                amountPerson.setText("");
                                                                toAccountNum.setText("");
                                                                availNum.setText(String.format("%.2f", user.getAccount().getBalance()));
                                                            }
                                                        });

                                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                            else {
                                                AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
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
                                    }
                                });

                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                    else{
                        if(user.getAccount().getBalance()-Double.parseDouble(amountPerson.getText().toString())>=0) {
                            Account acc = new Account(Integer.parseInt(toAccountNum.getText().toString()), 500, 10000);
                            Contact newContact = new Contact(contactNameSave.getText().toString(), acc);
                            user.addContact(newContact);
                            user.getAccount().transferFund(Double.parseDouble(String.format("%.2f", Double.parseDouble(amountPerson.getText().toString()))), acc);
                            amountPerson.setText("");
                            toAccountNum.setText("");
                            contactNameSave.setText("");
                            availNum.setText(String.format("%.2f",user.getAccount().getBalance()));
                        }
                        else{
                            if(Math.abs(user.getAccount().getBalance()-Double.parseDouble(amountPerson.getText().toString()))<=user.getAccount().getOverdraftLimit()) {
                                AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
                                alertDialog.setTitle("Warning");
                                alertDialog.setMessage("You will now enter in your Overdraft, Continue?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Account acc = new Account(Integer.parseInt(toAccountNum.getText().toString()), 500, 10000);
                                                Contact newContact = new Contact(contactNameSave.getText().toString(), acc);
                                                user.addContact(newContact);
                                                user.getAccount().transferFund(Double.parseDouble(String.format("%.2f", Double.parseDouble(amountPerson.getText().toString()))), acc);
                                                amountPerson.setText("");
                                                toAccountNum.setText("");
                                                contactNameSave.setText("");
                                                availNum.setText(String.format("%.2f", user.getAccount().getBalance()));
                                            }
                                        });

                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
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
                    }
                }
                else{
                    if(amountPerson.getText().toString().equals("")){
                        AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Empty Money field");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(PayPersonActivity.this).create();
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

        cancelButton = (Button)findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_person, menu);
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

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.i("USER", "Something happened");
        MainActivity.getTimeCounter().resetTimer();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... params) {
            //If user idle for 60 seconds log him out
            while(MainActivity.getTimeCounter().countTime()<60000){}
            finish();

            return null;
        }

    }
}
