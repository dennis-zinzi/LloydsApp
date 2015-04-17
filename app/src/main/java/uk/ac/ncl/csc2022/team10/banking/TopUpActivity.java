package uk.ac.ncl.csc2022.team10.banking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;

/*
    author: szholdiyarov
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;


public class TopUpActivity extends ActionBarActivity {

    /* Declaration of variables */
    private TextView walletNameView;
    private TextView currentBalanceView;
    private EditText amountTopUp;
    private Button buttonTopUp;
    private Button buttonClose;
    private String walletsName;
    private double balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        Bundle b = getIntent().getExtras();

        walletsName = b.getString("name");
        balance = b.getDouble("balance1");
        walletNameView = (TextView) findViewById(R.id.textView_Wallet);
        currentBalanceView = (TextView) findViewById(R.id.textView_CurrentBalanceDisplay);
        amountTopUp = (EditText) findViewById(R.id.editText_TopUp);

        walletNameView.setText(walletsName);
        currentBalanceView.setText(String.format("%.2f", balance));

        buttonTopUp = (Button) findViewById(R.id.button_topUp);
        buttonClose = (Button) findViewById(R.id.button_close);
        final Context context = this;
        buttonTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountTopUp.getText().toString();
                if (amountTopUp.getText().toString().matches("")) {
                    new AlertDialog.Builder(context)
                            .setTitle("Something wrong")
                            .setMessage("Please enter amount")
                            .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    try {
                        MainActivity.setNewWalletBalance(walletsName, Double.parseDouble(amount));
                        MainActivity.getUser().getAccount().transferFund(Double.parseDouble(amount), MainActivity.getUser().getAccount());
                        printAlertSuccess(context);
                    } catch (NumberFormatException e) {
                        String modified = amount + ".00";
                        MainActivity.setNewWalletBalance(walletsName, Double.parseDouble(modified));
                        MainActivity.getUser().getAccount().transferFund(Double.parseDouble(modified), MainActivity.getUser().getAccount());
                        printAlertSuccess(context);
                    }
                }

            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_top_up, menu);
        return true;
    }

    public void printAlertSuccess(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Well done")
                .setMessage("Your balance has been updated")
                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_help){
            Intent i = new Intent(this,HelpActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_logout){
            setResult(2);
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
            setResult(2);
            finish();

            return null;
        }

    }
}
