package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
//import android.view.*;
//import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
//import android.graphics.Color;
//import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import uk.ac.ncl.csc2022.team10.datatypes.Account;
import uk.ac.ncl.csc2022.team10.datatypes.Contact;
import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.datatypes.Wallet;
import uk.ac.ncl.csc2022.team10.encryption.Encryption;

public class LoginActivity extends Activity implements OnClickListener {

    private Button loginButton;
    private Button exitButton;
    private EditText account;
    private EditText password;
    private final static String USER_AGENT = "Mozilla/5.0";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        final Context context = this;
        final Intent intent1 = new Intent(this, MainActivity.class);
        final MyAsyncTask asyncTask = new MyAsyncTask();

        loginButton = (Button) findViewById(R.id.login);
        exitButton = (Button) findViewById(R.id.exit);

        //Add listener using Anonymous class
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        Integer result = asyncTask.execute().get();
                        if (result == 1) {
                            account = (EditText) findViewById(R.id.toContact);
                            Log.i("MY SYSTEM", "All passed");
                            makeUser("Den", account.getText().toString());
                            MainActivity.setUser(user);
                            startActivity(intent1);
                        } else {
                            Log.i("MY SYSTEM", "BAD");
                            new AlertDialog.Builder(context)
                                    .setTitle("Something wrong")
                                    .setMessage("Please check your details and try again")
                                    .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            });


            exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    /* Exit app */
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void makeUser(String name,String userId){
        Account a = new Account(1,10000,1000);
        user = new User(name, userId,a);
        Wallet phone = new Wallet("Phone", 2.5);
        Wallet google = new Wallet("Google", 5);
        user.addWallet(phone);
        user.addWallet(google);
        user.addContact(new Contact("Tom",new Account(2,50,1000)));
        user.addContact(new Contact("Sanzhar",new Account(3,190,5000)));
        user.addContact(new Contact("Rhys",new Account(4,500,10000)));
        user.addContact(new Contact("Ashley",new Account(5,300,100)));
    }


    private class MyAsyncTask extends AsyncTask<String, Void, Integer> {
        /** Essential data types **/
        private LoginActivity ac = new LoginActivity();
        private String enteredAccount;
        private String encrypted;
        private String enteredPassword;
        private Encryption enc;
        private String url;
        private URL obj;
        private HttpURLConnection con1;
        private String urlParameters;
        private DataOutputStream wr;
        private BufferedReader in;
        private String inputLine;

        @Override
        protected Integer doInBackground(String... params) {
            account = (EditText) findViewById(R.id.toContact);
            password = (EditText) findViewById(R.id.editText3);
            enc = new Encryption();
            enteredAccount = account.getText().toString();
            enteredPassword = password.getText().toString();
            encrypted = enc.encrypt(enteredPassword);
            Log.i("MyAsyncTask", "Here what i have : " + enteredAccount + ", " + enteredPassword + ", " + encrypted);
            try {
                url = "http://zholdiyarov.zz.mu/encrypt.php";
                obj = new URL(url);
                con1 = (HttpURLConnection) obj.openConnection();

                con1.setRequestMethod("POST");
                con1.setRequestProperty("User-Agent", USER_AGENT);
                con1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con1.setDoOutput(true);

                urlParameters = "pwd=" + encrypted + "&account=" + enteredAccount;

                wr = new DataOutputStream(con1.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                in = new BufferedReader(new InputStreamReader(
                        con1.getInputStream()));

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("good")) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
                in.close();
            } catch (Exception e) {
                return 0;
            }
            return 0;
        }
    }

}