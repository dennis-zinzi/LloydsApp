package uk.ac.ncl.csc2022.team10.banking;
/*
    author: szholdiyarov
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.ncl.csc2022.team10.lloydsapp.R;


public class PayBills extends ActionBarActivity {

    private Button button_PayBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bills);
        final Context context = this;
        button_PayBill = (Button) findViewById(R.id.button);
        button_PayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Pay bill")
                        .setMessage("Your pay bill request has been accepted")
                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_bills, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
