package uk.ac.ncl.csc2022.team10.banking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.WalletFragment;


public class TopUpActivity extends ActionBarActivity {

    private TextView walletNameView;
    private TextView currentBalanceView;
    private EditText amountTopUp;
    private User u;
    private String walletsName;
    private double balance;
    private Button buttonTopUp;
    private Button buttonClose;

    public TopUpActivity() {
        u = MainActivity.getUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TopUp", "onCreate is called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        Bundle b = getIntent().getExtras();
        walletsName = b.getString("name");
        balance = b.getDouble("balance1");
        Log.i("TopUp", "with " + walletsName + " " + balance);
        walletNameView = (TextView) findViewById(R.id.textView_Wallet);
        currentBalanceView = (TextView) findViewById(R.id.textView_CurrentBalanceDisplay);
        amountTopUp = (EditText) findViewById(R.id.editText_TopUp);

        walletNameView.setText(walletsName);
        currentBalanceView.setText(String.format("%.2f", balance));

        buttonTopUp = (Button) findViewById(R.id.button_topUp);
        buttonClose = (Button) findViewById(R.id.button_close);
        final Intent intent1 = new Intent(this, WalletFragment.class);
        u = MainActivity.getUser();
        final Context context = this;
        buttonTopUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Wallet w = u.getWalletByName(walletsName);
                //Log.i("TopUpActivity","Wallet:"+w.getName());
                //w.setBalance(Double.parseDouble(amountTopUp.getText().toString()));
                //startActivity(intent1);
                Log.i("TopUp", "button is clicked");
                MainActivity.setNewWalletBalance(walletsName, Double.parseDouble(amountTopUp.getText().toString()));
                MainActivity.getUser().getAccount().transferFund(Double.parseDouble(amountTopUp.getText().toString()), MainActivity.getUser().getAccount());
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
                Log.i("Wallets", "New balance");
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
}
