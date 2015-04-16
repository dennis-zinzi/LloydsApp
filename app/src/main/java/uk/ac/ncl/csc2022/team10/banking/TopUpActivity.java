package uk.ac.ncl.csc2022.team10.banking;

import android.support.v7.app.ActionBarActivity;

/* Created by szholdiyarov */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.WalletFragment;


public class TopUpActivity extends ActionBarActivity {

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
        final Intent intent1 = new Intent(this, WalletFragment.class);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_top_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
