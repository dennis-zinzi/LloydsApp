package uk.ac.ncl.csc2022.team10.banking;
/*
    author: szholdiyarov
    Purpose: Show standing activity
 */

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.csc2022.team10.lloydsapp.R;


public class StandingOrders extends ActionBarActivity {
    /* Declaration of variables */
    private String[] values;
    private ListView listView;
    private ArrayList<String> list;
    private StableArrayAdapter adapter;

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_standing_orders, menu);
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

}
