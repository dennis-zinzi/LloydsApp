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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;

public class ProductInfoActivity extends ActionBarActivity {

    List<String> info;
    ListView accountInfoList;
    TextView accountSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_product_info);

        setTitle("Account Information");

        info = new ArrayList<String>();
        info.add("Interest and fee free Planned Overdraft of up to £1,500 in years 1-3 and up to £2,000 in years 4-6 " +
                      "(subject to application status and approval - you must be over 18 to apply)");
        info.add("Earn up to 15% with Everyday Offers and the chance to win one of your purchases back up to the " +
                        "value of £500 on a monthly basis with It’s on Us (Internet Banking registration required. " +
                        "You must be aged 18 or over)");
        info.add("Lloyds Bank Visa debit card to use at home and abroad");
        info.add("Option to apply for a Student Mastercard Credit Card (subject to application status and approval " +
                        "- you must be 18 or over to apply)");
        info.add("A range of ways to manage your money with our Mobile Banking App and Pay a Contact services");
        info.add("Manage your money 24/7 with Internet Banking, PhoneBank, Mobile Banking and Money Manager");

        accountInfoList = (ListView)findViewById(R.id.accountInfoList);
        accountInfoList.setAdapter(new ArrayAdapter<String>(this, R.layout.listrows, info));

        accountSite = (TextView)findViewById(R.id.accountSite);
        accountSite.setText("For more information on your Lloyds Bank Student Account, visit our website: http://www.lloydsbank.com/current-accounts/student-account.asp (Tap here to access)");

        accountSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.lloydsbank.com/current-accounts/student-account.asp"));
                startActivity(intent);
            }
        });

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_info, menu);
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
