package uk.ac.ncl.csc2022.team10.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.*;
import java.util.*;

import uk.ac.ncl.csc2022.team10.lloydsapp.HelpActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;
import uk.ac.ncl.csc2022.team10.lloydsapp.R;
import uk.ac.ncl.csc2022.team10.lloydsapp.SettingsActivity;

public class FAQActivity extends ActionBarActivity {

    ListView faqQuestionList;
    List<String> questionList;
    List<String> answerList;
    ArrayAdapter<String> faqQuestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_faq);

        setTitle("FAQs");

        faqQuestionList = (ListView) findViewById(R.id.faqQuestionsList);
        questionList = new ArrayList<String>();
        answerList = new ArrayList<String>();
        makeQuestions();
        makeAnswers();
        faqQuestionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listrows, questionList);
        faqQuestionList.setAdapter(faqQuestionsAdapter);

        faqQuestionsAdapter.notifyDataSetChanged();

        faqQuestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(FAQActivity.this).create();
                alertDialog.setTitle(questionList.get(position));
                alertDialog.setMessage(answerList.get(position));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        new AsyncCaller().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faq, menu);
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

    public void makeQuestions(){
        String q1 = "What can I do using th Lloyds Student Mobile Banking App?";
        String q2 = "Is Mobile Banking Secure?";
        String q3 = "Can I change my Login details?";
        String q4 = "I'd like to contact my bank, where can I find this information?";
        String q5 = "What can I do with my Virtual Points?";
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        questionList.add(q4);
        questionList.add(q5);
    }

    public void makeAnswers(){
        String a1 =
                "Our Mobile Banking app allows you to:\n" +
                        "- View your account and account balance\n" +
                        "- View your transaction statement\n" +
                        "- Add and mange your budgets for various transactions\n" +
                        "- Make a fast and simple QR payment, or scan a generated QR code to receive a transfer\n" +
                        "- Pay an existing payee who you’ve paid before, or create a new contact\n" +
                        "- Create a virtual wallet and reserve money to spend on a specific service\n" +
                        "- Set budgets and earn virtual points\n" +
                        "- Find your nearest Lloyds branch at the touch of a button!";
        String a2 =
                "The security of your data is our highest priority, and we have a number of security " +
                        "barriers in place to help ensure your data is keep safe. Our log on process " +
                        "requires dual pieces of information to gain access to your accounts, and allows " +
                        "you to set your own easy-to-remember password in-app. Whats more, our application " +
                        "logs you out after you remain inactive for a given period of time to further protect " +
                        "your data if your device is lost or stolen.\n";
        String a3 =
                "Yes. You can change your password for the application by tapping the ‘Change Password’ button, " +
                        "under the ‘Settings’ tab of the drop-down menu. We recommend you change your password " +
                        "regularly as a further precautionary step to ensuring the security of your data.";
        String a4 =
                "You can find contact information including Lloyds Banking group HQ postal address, phone numbers, " +
                        "and website through accessing the ‘Contact Us’ page. Alternatively, you can find your nearest " +
                        "branch and ATM using the ‘Find Nearest Branch’ feature if you would like to speak to a member " +
                        "of staff in-branch. Both of these features can be found under the ‘Help’ tab.";
        String a5 =
                "Virtual points are our way of rewarding you! You can use your virtual points to exchange for a wide " +
                        "range of incentives suppliers by Lloyds bank partners. Start earning points today for your " +
                        "chance to get your hands on your great prizes!";

        answerList.add(a1);
        answerList.add(a2);
        answerList.add(a3);
        answerList.add(a4);
        answerList.add(a5);
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
