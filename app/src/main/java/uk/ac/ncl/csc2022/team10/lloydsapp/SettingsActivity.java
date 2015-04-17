package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.ncl.csc2022.team10.datatypes.User;

public class SettingsActivity extends ActionBarActivity {

    Button passwordChange;
    EditText oldPassword;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);
        user = MainActivity.getUser();
        passwordChange = (Button)findViewById(R.id.passwordChange);
        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                LayoutInflater inflater = SettingsActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.dialog_password,null))
                        .setTitle("Change Password")
                        .setPositiveButton("CONFRIM",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog f = (Dialog) dialog;
                                //This is the input I can't get text from
                                EditText oldPassword = (EditText) f.findViewById(R.id.oldPassword);
                                EditText newPassword = (EditText) f.findViewById(R.id.newPassword);
                                EditText confirmPassword = (EditText) f.findViewById(R.id.confirmPassword);

                                if(user.getPassword().equals(oldPassword.getText().toString()) &&
                                        newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                                    user.setPassword(newPassword.getText().toString());
                                }
                                else{
                                    if(!user.getPassword().equals(oldPassword.getText().toString())){
                                        AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();
                                        alertDialog.setTitle("Warning");
                                        alertDialog.setMessage("Password incorrect");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                    else{
                                        AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();
                                        alertDialog.setTitle("Warning");
                                        alertDialog.setMessage("New Password doesn't match");
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
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder.show();
            }

        });

        //new AsyncCaller().execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new AsyncCaller().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new AsyncCaller().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_help){
            Intent i = new Intent(this,HelpActivity.class);
            startActivity(i);
            finish();
        }
        else if(id == R.id.action_logout){
            setResult(1);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
            setResult(1);
            finish();

            return null;
        }

    }
}