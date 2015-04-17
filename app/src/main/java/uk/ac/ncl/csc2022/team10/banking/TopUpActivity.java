package uk.ac.ncl.csc2022.team10.banking;

import android.support.v7.app.ActionBarActivity;

/*
    author: szholdiyarov
    purpose: This is activity which allow user to top up their wallets.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;


public class TopUpActivity extends ActionBarActivity {

    /* Declaration of variables */
    private TextView textView_walletName;
    private TextView textView_currentBalance;
    private EditText editText_amount;
    private Button button_TopUp;
    private Button button_Close;
    private String walletName;
    private String amount;
    private double convertedAmount;
    private double balance;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        /* Get parameters */
        Bundle b = getIntent().getExtras();
        walletName = b.getString("name");
        balance = b.getDouble("balance");
        /* Set textViews, edit text field and buttons */
        textView_walletName = (TextView) findViewById(R.id.textView_Wallet);
        textView_currentBalance = (TextView) findViewById(R.id.textView_CurrentBalanceDisplay);
        editText_amount = (EditText) findViewById(R.id.editText_TopUp);
        button_TopUp = (Button) findViewById(R.id.button_topUp);
        button_Close = (Button) findViewById(R.id.button_close);
        /* Set appropriate wallet name and current balance to display */
        textView_walletName.setText(walletName);
        textView_currentBalance.setText(String.format("%.2f", balance));
        context = this;

        /* Set buttons listeners */
        button_TopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_amount.getText().toString().matches("")) { // If no input
                    printAlertSomethingWrong(context);
                } else {
                    /* Get text and convert it to double */
                    amount = editText_amount.getText().toString();
                    convertedAmount = Double.parseDouble(amount);
                    try {
                        topUp(convertedAmount);
                        printAlertSuccess(context);
                    } catch (NumberFormatException e) { // If exception => try again
                        topUp(convertedAmount);
                        printAlertSuccess(context);
                    }
                }

            }
        });
        button_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish activity
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

    /* Show alert dialog with "success" */
    public void printAlertSuccess(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Well done")
                .setMessage("Your balance has been updated")
                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    /* Show alert dialog with "try again" */
    public void printAlertSomethingWrong(Context context) {
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

    }
    /* Set new balance to wallet, remove it from account and transfer it*/
    public void topUp(double convertedAmount) {
        MainActivity.setNewWalletBalance(walletName, convertedAmount);
        MainActivity.getUser().getAccount().transferFund(convertedAmount, MainActivity.getUser().getAccount());
        MainActivity.getUser().getAccount().removeBalance(convertedAmount);
    }
}
