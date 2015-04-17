package uk.ac.ncl.csc2022.team10.banking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

import uk.ac.ncl.csc2022.team10.datatypes.Transaction;
import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;

/**
 * Created by: Dennis Zinzi
 */
public class StatementActivity extends ActionBarActivity {

    User user;
    TextView balanceNum;
    ListView statementList;
    ArrayAdapter<String> transactionList;
    List<String> transactionString;
    List<Transaction> transactions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_statement);
        user = MainActivity.getUser();
        getTransactionList();

        setTitle("Statement");

        balanceNum = (TextView)findViewById(R.id.balanceNum);
        balanceNum.setText(String.format("%.2f",user.getAccount().getBalance()));

        statementList = (ListView)findViewById(R.id.statementList);

        Context context = getApplicationContext();

        transactionList = new ArrayAdapter<String>(context, R.layout.listrows, transactionString);

        statementList.setAdapter(transactionList);

        //banks.add("TSB Heaton");
        transactionList.notifyDataSetChanged();

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statement, menu);
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
        if(id == R.id.action_help){
            Intent i = new Intent(this, HelpActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_logout){
            setResult(1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTransactionList(){
        transactions = user.getAccount().getTransaction();
        transactionString = new ArrayList<String>();

        for (Transaction t : transactions) {
            transactionString.add(t.toString());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If requestCode of parent equals 2 and resultCode of child is 1, then
        // child requested to logout, thus MainActivity should finish
        if (requestCode == 3) {
            if (resultCode == 2) {
                setResult(1);
                finish();
            }
        }
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
            finish();

            return null;
        }

    }
}
