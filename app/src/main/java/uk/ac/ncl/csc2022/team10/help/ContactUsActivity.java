package uk.ac.ncl.csc2022.team10.help;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;

/**
 * Created by: Dennis Zinzi
 */
public class ContactUsActivity extends ActionBarActivity {

    ListView contactList;
    String[] contacts = {
            "http://www.lloydsbankinggroup.com",
            "Lloyds Bank Customer Enquiries:\n\n0845 300 0000",
            "Lloyds 24 hour lost and stolen cards helpline:\n\n0800 096 977",
            "London Head Office:\n" +
                    "\n" +
                    "25 Gresham Street\n" +
                    "London\n" +
                    "EC2V 7HN\n" +
                    "Tel: +44 (0) 20 7626 1500\n"
    };
    Integer[] contactImages = {
            R.drawable.globe,
            R.drawable.green_phone,
            R.drawable.green_phone,
            R.drawable.home_lloyds
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_contact_us);

        setTitle("Contact Us");

        contactList = (ListView)findViewById(R.id.contactList);
        ComboAdapter adapter = new ComboAdapter(this,contacts,contactImages);
        contactList.setAdapter(adapter);

        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    String url = "http://www.lloydsbankinggroup.com";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                else if(position==1){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:08453000000"));
                    startActivity(callIntent);
                }
                else if(position==2){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:0800096977"));
                    startActivity(callIntent);
                }
                else if (position==3){
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:02076261500"));
                    startActivity(callIntent);
                }
                return false;
            }
        });

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_us, menu);
        return true;
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
