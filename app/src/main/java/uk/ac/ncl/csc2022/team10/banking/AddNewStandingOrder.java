package uk.ac.ncl.csc2022.team10.banking;

/**
 *  Created by Sanzhar Zholdiyarov
 * Purpose: Activity to add new standing orders
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.ncl.csc2022.team10.lloydsapp.R;

public class AddNewStandingOrder extends ActionBarActivity {
    /* Declarations of variables */
    private Button buttonAdd;
    private EditText nameEditText;
    private EditText amountEditText;
    private EditText dateEditText;
    private String newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_standing_order);
        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_standing_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handling action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Add listeners to button */
    public void addListenerOnButton() {
        buttonAdd = (Button) findViewById(R.id.button_standing_order_add);
        nameEditText = (EditText) findViewById(R.id.editText_standing_order_name);
        amountEditText = (EditText) findViewById(R.id.editText_standing_order_amount);
        dateEditText = (EditText) findViewById(R.id.editText_standing_order_date);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             /* Get new order details */
                                             newOrder = nameEditText.getText().toString() + " | " + amountEditText.getText().toString() + " | " + dateEditText.getText().toString() + " | To pay";
                                             StandingOrders.addToList(newOrder);
                                             finish();
                                         }
                                     }

        );
    }
}
