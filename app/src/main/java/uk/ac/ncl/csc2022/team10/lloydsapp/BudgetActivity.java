package uk.ac.ncl.csc2022.team10.lloydsapp;

/**
 * Created by Rhys Covell
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_help) {
            Intent i = new Intent(this, HelpActivity.class);
            // Starts Activity and gives it requestCode = 2
            startActivityForResult(i, 2);
            return true;
        } else if (id == R.id.action_logout) {
            // End MainActivity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void buildPage() {

        ScrollView scroll = new ScrollView(this);

        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setBackgroundColor(0xFF105942);
        LayoutParams paramContainer = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scroll.addView(container, paramContainer);

        Button addBudget = new Button(this);
        addBudget.setText("Add Budget");
        addBudget.setBackgroundColor(Color.WHITE);
        addBudget.setGravity(Gravity.CENTER);
        addBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this);

                final EditText name = new EditText(BudgetActivity.this);
                final EditText limit = new EditText(BudgetActivity.this);

                name.setHint("Name");
                limit.setHint("Limit");
                limit.setInputType(InputType.TYPE_CLASS_NUMBER);

                LinearLayout alertLayout = new LinearLayout(getApplication());
                alertLayout.setOrientation(LinearLayout.VERTICAL);
                alertLayout.addView(name);
                alertLayout.addView(limit);
                builder.setView(alertLayout);

                builder.setMessage("Add budget details: ")
                        .setTitle("New Budget")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!name.getText().equals(null) && !limit.getText().equals(null)) {
                                    Budget b = new Budget(name.getText().toString(), Integer.parseInt(limit.getText().toString()));
                                    user.addBudget(b);
                                    recreate();
                                }

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


        container.addView(addBudget);


        for (int i = 0; i < budgets.size(); i++) {


            final LinearLayout containerBudgets = new LinearLayout(this);
            containerBudgets.setId(i);
            containerBudgets.setPadding(20, 20, 20, 20);
            containerBudgets.setOrientation(LinearLayout.VERTICAL);
            containerBudgets.setBackgroundColor(Color.WHITE);
            LayoutParams paramContainerBudgets = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            paramContainerBudgets.setMargins(10, 10, 10, 10);

            containerBudgets.setOnLongClickListener(new OnLongClickListener() {

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

            container.addView(containerBudgets, paramContainerBudgets);

            LinearLayout containerBudgetTop = new LinearLayout(this);
            containerBudgetTop.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams paramContainerBudgetTop = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            containerBudgets.addView(containerBudgetTop, paramContainerBudgetTop);

            TextView budgetName = new TextView(this);
            budgetName.setText(budgets.get(i).getName());
            budgetName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            containerBudgetTop.addView(budgetName);

            Button editBudget = new Button(this);
            LayoutParams editBudgetParam = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editBudget.setText("Edit");
            editBudgetParam.gravity = Gravity.RIGHT;
            editBudget.setBackgroundColor(Color.WHITE);
            editBudget.setOnClickListener(new View.OnClickListener() {

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
            containerBudgetTop.addView(editBudget, editBudgetParam);


            ProgressBar Pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            Pb.setProgress((int) (budgets.get(containerBudgets.getId()).getCurrentSpend() * 100 / budgets.get(containerBudgets.getId()).getLimit()));
            Pb.setPadding(10, 0, 10, 0);
            containerBudgets.addView(Pb);


            TextView currentSpend = new TextView(this);

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
            containerBudgets.addView(currentSpend);



        }
    }
}