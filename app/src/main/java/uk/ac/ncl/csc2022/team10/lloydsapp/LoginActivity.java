package uk.ac.ncl.csc2022.team10.lloydsapp;

import android.app.Activity;
//import android.view.*;
//import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
//import android.graphics.Color;
//import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener{


    Button loginButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        //Determine button to add listener to
        loginButton = (Button) findViewById(R.id.login);
        exitButton = (Button)findViewById(R.id.exit);

        //Add listener using Anonymous class
        loginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

				/*
				 * Code to verify login details and log user into main menu
				 */

				/*
				 * Code to move to Passcode Login
				 * Intent intent = new Intent(context, PasscodeLoginActivity.class);
				 * startActivity(intent);
				 */

                //Go to menu for now
                Intent intent = new Intent(context, MainActivity.class);
//				Intent intent = new Intent(context, MainMenuActivity.class);

                startActivity(intent);

            }

        });

        exitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Exits app
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}