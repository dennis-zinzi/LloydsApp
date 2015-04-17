package uk.ac.ncl.csc2022.team10.budgeting;

/**
 * Created by Rhys Covell
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.List;

import uk.ac.ncl.csc2022.team10.datatypes.Budget;
import uk.ac.ncl.csc2022.team10.datatypes.User;
import uk.ac.ncl.csc2022.team10.lloydsapp.MainActivity;


public class BudgetActivity extends Activity {
    List<Budget> budgets;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Budgeting");
        user = MainActivity.getUser();
        budgets = user.getBudgets();

        buildPage();

        new AsyncCaller().execute();

    }

    public void buildPage() {

        ScrollView scroll = new ScrollView(this);                               //Make page scrollable
        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,      //
                LayoutParams.WRAP_CONTENT));                                    //
        setContentView(scroll);                                                 //

        LinearLayout container = new LinearLayout(this);                        //Scroll can only hold one
        container.setOrientation(LinearLayout.VERTICAL);                        //layout, this acts as a holder.
        container.setBackgroundColor(0xFF105942);                               //
        LayoutParams paramContainer = new LayoutParams                          //Everything else added to it.
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);         //
        scroll.addView(container, paramContainer);                              //

        Button addBudget = new Button(this);                //Creates the Add Budget button.
        addBudget.setText("Add Budget");                    //
        addBudget.setBackgroundColor(Color.WHITE);          //
        addBudget.setGravity(Gravity.CENTER);               //
        addBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {               //

                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this); //setup for the dialogue for users to interact with

                final EditText name = new EditText(BudgetActivity.this); //Fields for them to enter information about their budget.
                final EditText limit = new EditText(BudgetActivity.this);//

                name.setHint("Name");
                limit.setHint("Limit");
                limit.setInputType(InputType.TYPE_CLASS_NUMBER);//changes input type for keyboard

                LinearLayout alertLayout = new LinearLayout(getApplication());//
                alertLayout.setOrientation(LinearLayout.VERTICAL);            //
                alertLayout.addView(name);                                    //Gives the dialogue a layout
                alertLayout.addView(limit);                                   //
                builder.setView(alertLayout);                                 //

                builder.setMessage("Add budget details: ")
                        .setTitle("New Budget")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!name.getText().toString().equals("") && !limit.getText().toString().equals("")) //Only make budget if user provide all information
                                {
                                    Budget b = new Budget(name.getText().toString(), Integer.parseInt(limit.getText().toString()));
                                    user.addBudget(b);
                                    recreate(); //recreate page to update budget to page
                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do Nothing if cancelled
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show(); //Show user the made dialogue


            }
        });


        container.addView(addBudget); //add the button to page.


        for (int i = 0; i < budgets.size(); i++) { //for each of the budgets make a layout with their data.


            final LinearLayout containerBudgets = new LinearLayout(this);   //Create the layout
            containerBudgets.setId(i);                                      //Give it an identity (used for identifying which budget to delete)
            containerBudgets.setPadding(20, 20, 20, 20);                    //
            containerBudgets.setOrientation(LinearLayout.VERTICAL);         //
            containerBudgets.setBackgroundColor(Color.WHITE);               //
            LayoutParams paramContainerBudgets = new LayoutParams           //
                    (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); //
            paramContainerBudgets.setMargins(10, 10, 10, 10);               //

            containerBudgets.setOnLongClickListener(new OnLongClickListener()  //On a long click let the use edit the fields or delete the budget
            {

                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this);

                    final EditText name = new EditText(BudgetActivity.this);
                    final EditText limit = new EditText(BudgetActivity.this);
                    final EditText spend = new EditText(BudgetActivity.this);
                    final CheckBox deleteBox = new CheckBox(BudgetActivity.this);
                    name.setHint("Name");
                    limit.setHint("Limit");
                    limit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    spend.setHint("Current spend");
                    spend.setInputType(InputType.TYPE_CLASS_NUMBER);
                    deleteBox.setText("Delete");
                    LinearLayout alertLayout = new LinearLayout(getApplication());
                    alertLayout.setOrientation(LinearLayout.VERTICAL);
                    alertLayout.addView(name);
                    alertLayout.addView(limit);
                    alertLayout.addView(spend);
                    alertLayout.addView(deleteBox);
                    builder.setView(alertLayout);

                    builder.setMessage("Budget Name: ")
                            .setTitle("New Budget")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    if (!name.getText().toString().equals("")) {
                                        budgets.get(containerBudgets.getId()).setName(name.getText().toString());
                                    }

                                    try {

                                        budgets.get(containerBudgets.getId()).setLimit(Double.valueOf(limit.getText().toString()));


                                    } catch (NumberFormatException e) {
                                    }

                                    try {
                                        budgets.get(containerBudgets.getId()).setCurrentSpend(Double.valueOf(spend.getText().toString()));
                                    } catch (NumberFormatException e) {
                                    }

                                    if (deleteBox.isChecked()) {
                                        budgets.remove(containerBudgets.getId());
                                    }


                                    recreate();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do Nothing
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;

                }
            });

            container.addView(containerBudgets, paramContainerBudgets); //Add the layout (Only the white box so far)

            LinearLayout containerBudgetTop = new LinearLayout(this); //New layout (linear horizontal) for holding button next to budget name
            containerBudgetTop.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams paramContainerBudgetTop = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            containerBudgets.addView(containerBudgetTop, paramContainerBudgetTop);

            TextView budgetName = new TextView(this);           //Add the budget name to the layout
            budgetName.setText(budgets.get(i).getName());
            budgetName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            containerBudgetTop.addView(budgetName);

            Button editBudget = new Button(this);           //Add the edit button to the layout
            LayoutParams editBudgetParam = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editBudget.setText("Edit");
            editBudgetParam.gravity = Gravity.RIGHT;
            editBudget.setBackgroundColor(Color.WHITE);
            editBudget.setOnClickListener(new View.OnClickListener() //This is the same as the long click users can perform
            {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this);

                    final EditText name = new EditText(BudgetActivity.this);
                    final EditText limit = new EditText(BudgetActivity.this);
                    final EditText spend = new EditText(BudgetActivity.this);
                    final CheckBox deleteBox = new CheckBox(BudgetActivity.this);
                    name.setHint("Name");
                    limit.setHint("Limit");
                    limit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    spend.setHint("Current spend");
                    spend.setInputType(InputType.TYPE_CLASS_NUMBER);
                    deleteBox.setText("Delete");
                    LinearLayout alertLayout = new LinearLayout(getApplication());
                    alertLayout.setOrientation(LinearLayout.VERTICAL);
                    alertLayout.addView(name);
                    alertLayout.addView(limit);
                    alertLayout.addView(spend);
                    alertLayout.addView(deleteBox);
                    builder.setView(alertLayout);

                    builder.setMessage("Budget Name: ")
                            .setTitle("New Budget")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    if (!name.getText().toString().equals("")) {
                                        budgets.get(containerBudgets.getId()).setName(name.getText().toString());
                                    }

                                    try {

                                        budgets.get(containerBudgets.getId()).setLimit(Double.valueOf(limit.getText().toString()));


                                    } catch (NumberFormatException e) {
                                    }

                                    try {
                                        budgets.get(containerBudgets.getId()).setCurrentSpend(Double.valueOf(spend.getText().toString()));
                                    } catch (NumberFormatException e) {
                                    }

                                    if (deleteBox.isChecked()) {
                                        budgets.remove(containerBudgets.getId());
                                    }


                                    recreate();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do Nothing
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });
            containerBudgetTop.addView(editBudget, editBudgetParam); //Add the button to layout (next to budget name)


            ProgressBar Pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal); //Add a ProgBar Horiz, shows user progress of spending.
            Pb.setProgress((int) (budgets.get(containerBudgets.getId()).getCurrentSpend() * 100 //gets percentage of spend compared to limit.
                    / budgets.get(containerBudgets.getId()).getLimit()));
            Pb.setPadding(10, 0, 10, 0);
            containerBudgets.addView(Pb);//Add progressBar below Budget name & edit button.


            TextView currentSpend = new TextView(this); //Text view to show budget data linguistically.

            if ((int) budgets.get(containerBudgets.getId()).getCurrentSpend() > (int) budgets.get(containerBudgets.getId()).getLimit()) {
                currentSpend.setText("Current spend: £" + (int) budgets.get(containerBudgets.getId()).getCurrentSpend() +
                        "\nLimit: £" + (int) budgets.get(containerBudgets.getId()).getLimit()
                        + "\nOverspent: -£" + Math.abs(((int) (budgets.get(containerBudgets.getId()).getLimit()) - ((int) budgets.get(containerBudgets.getId()).getCurrentSpend()))));

            } else {
                currentSpend.setText("Current spend: £" + (int) budgets.get(containerBudgets.getId()).getCurrentSpend() +
                        "\nLimit: £" + (int) budgets.get(containerBudgets.getId()).getLimit());

            }

            currentSpend.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            currentSpend.setPadding(0, 0, 0, 10);
            containerBudgets.addView(currentSpend);//Add below ProgBar.



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
            setResult(2);
            finish();

            return null;
        }

    }
}