package uk.ac.ncl.csc2022.team10.banking;
/*
    author: szholdiyarov
    Purpose: Show standing activity
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.LoginActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;


public class StandingOrders extends ActionBarActivity {
    /* Declaration of variables */
    private String[] values;
    private ListView listView;
    private ArrayList<String> list;
    private StableArrayAdapter adapter;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing_orders);
        /* Set listView and put string values in list*/
        listView = (ListView) findViewById(R.id.listview);
        values = new String[]{"Sky Broadband | 15£ | 6.04 | Paid", "University Fees | 1000£ | 15.04 | Pending..", "Your Insurance | 30£ | 16.04 | To be paid",
                "Susan's Insurance | 30£ | 16.04 | To be paid", "Tom's Insurance | 30£ | 16.04 | To be paid", "TV Licence | 100£ | 18.04 | To be paid", "Electricity Bill | 20£ | 20.04 | To be paid", "Water Bill | 5£ | 21.04 | To be paid",
                "Car Rent | 50£ | 22.04 | To be paid", "Utility Bill | 15£ | 23.04 | To be paid", "School | 100£ | 27.04 | To be paid", "Sky TV | 15£ | 28.04 | To be paid"};

        list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        /* Set adapter */
        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        addListenerOnButton();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new AsyncCaller().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new AsyncCaller().execute();
        }
    }

    public void addListenerOnButton() {
        final Intent newIntent = new Intent(this, AddNewStandingOrder.class);
        buttonAdd = (Button) findViewById(R.id.button_addNew);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             startActivity(newIntent);
                                         }
                                     }

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_standing_orders, menu);
        return true;
    }

    /* Private class for custom ArrayAdapter*/

    private class StableArrayAdapter extends ArrayAdapter<String> {

        private HashMap<String, Integer> orders = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                orders.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return orders.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_help) {
            Intent i = new Intent(this, HelpActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_logout) {
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

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            //If user idle for 60 seconds log him out
            while (MainActivity.getTimeCounter().countTime() < 60000) {
            }
            setResult(2);
            finish();

            return null;
        }

    }

}
