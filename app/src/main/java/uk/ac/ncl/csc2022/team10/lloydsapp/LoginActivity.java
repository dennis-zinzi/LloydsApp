package uk.ac.ncl.csc2022.team10.lloydsapp;
/**
 * Created by Dennis.
 */
/*
    Modified by author: Sanzhar Zholdiyarov
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.encryption.Encryption;

public class LoginActivity extends Activity implements OnClickListener {

    private Button loginButton;
    private Button exitButton;
    private EditText account;
    private EditText password;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        final Context context = this;
        final Intent newIntent = new Intent(this, MainActivity.class);

        loginButton = (Button) findViewById(R.id.login);
        exitButton = (Button) findViewById(R.id.exit);

        //Add listener using Anonymous class
        loginButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyAsyncTask asyncTask;
                            asyncTask = new MyAsyncTask();
                            Integer result = asyncTask.execute().get();
                            if (result == 1) {

                                account = (EditText) findViewById(R.id.toContact);

                                Account a = new Account(1, 10000, 1000);
                                user = new User("Dennis", "123456", password.getText().toString(), a);
                                MainActivity.setUser(user);
                                MainActivity.setWallets();
                                MainActivity.setPoints();

                                startActivity(newIntent);
                                asyncTask.cancel(true);

                            } else {
                                new AlertDialog.Builder(context)
                                        .setTitle("Something wrong")
                                        .setMessage("Please check your details and try again")
                                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(LoginActivity.this,
                                                        LoginActivity.class);

                                                startActivity(intent);
                                                dialog.cancel();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();

                            }
                        } catch (
                                InterruptedException e
                                )

                        {
                            e.printStackTrace();
                        } catch (
                                ExecutionException e
                                )

                        {
                            e.printStackTrace();
                        }
                    }

                }

        );


        exitButton.setOnClickListener(new

                                              OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                    /* Exit app */
                                                      Intent intent = new Intent(Intent.ACTION_MAIN);
                                                      intent.addCategory(Intent.CATEGORY_HOME);
                                                      startActivity(intent);
                                                  }
                                              }

        );
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAsyncTask extends AsyncTask<String, Void, Integer> {
        /**
         * Essential data types *
         */
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