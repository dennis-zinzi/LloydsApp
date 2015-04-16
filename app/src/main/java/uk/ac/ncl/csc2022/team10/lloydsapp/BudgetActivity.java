package uk.ac.ncl.csc2022.team10.lloydsapp;



        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.pm.ActivityInfo;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.TypedValue;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.View;
        import android.view.View.OnLongClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.LinearLayout.LayoutParams;
        import android.widget.ProgressBar;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;

        import uk.ac.ncl.csc2022.team10.datatypes.User;
        import uk.ac.ncl.csc2022.team10.datatypes.Budget;


public class BudgetActivity extends Activity {
    List<Budget> budgets;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Budgeting");
        user = MainActivity.getUser();


        budgets = user.getBudgets();

        ScrollView scroll = new ScrollView(this);

        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setBackgroundColor(0xFF105942);
        LayoutParams paramContainer = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scroll.addView(container,paramContainer);

        Button addBudget = new Button(this);
        addBudget.setText("Add Budget");
        addBudget.setBackgroundColor(Color.WHITE);
        addBudget.setGravity(Gravity.CENTER);
        addBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this);

                final EditText input = new EditText(BudgetActivity.this);

                builder.setView(input);

                builder.setMessage("Budget Name: ")
                        .setTitle("New Budget")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String value = input.getText().toString();


                                Budget b = new Budget(value,100);
                                user.addBudget(b);
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


        container.addView(addBudget);

        budgets.add(new Budget("Grocery",60));
        budgets.add(new Budget("Phone",30));


        for(int i=0; i<budgets.size(); i++){

            Budget tempBudget = budgets.get(i);


            LinearLayout containerBudgets = new LinearLayout(this);
            containerBudgets.setPadding(20, 20, 20, 20);
            containerBudgets.setOrientation(LinearLayout.VERTICAL);
            containerBudgets.setBackgroundColor(Color.WHITE);
            LayoutParams paramContainerBudgets = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            paramContainerBudgets.setMargins(10, 10, 10, 10);

            containerBudgets.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this);

                    final EditText input = new EditText(BudgetActivity.this);

                    builder.setView(input);

                    builder .setMessage("Edit budget settings: ")
                            .setTitle("Edit Budget")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String value = input.getText().toString();
                                    Toast.makeText(getApplicationContext(),
                                            value, Toast.LENGTH_LONG).show();

                                    //Update to database query

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //Do Nothing
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;

                }
            });

            container.addView(containerBudgets,paramContainerBudgets);

            LinearLayout containerBudgetTop = new LinearLayout(this);
            containerBudgetTop.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams paramContainerBudgetTop = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            containerBudgets.addView(containerBudgetTop,paramContainerBudgetTop);

            TextView budgetName = new TextView(this);
            budgetName.setText(tempBudget.getName());
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

                    final EditText input = new EditText(BudgetActivity.this);

                    builder.setView(input);

                    builder.setMessage("Edit budget settings: ")
                            .setTitle("Edit Budget")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Do update to budget

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
            containerBudgetTop.addView(editBudget,editBudgetParam);




            ProgressBar Pb = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
            Pb.setProgress((int)(tempBudget.getCurrentSpend()/tempBudget.getLimit())*100);
            Pb.setPadding(10, 0, 10, 0);
            containerBudgets.addView(Pb);

            TextView currentSpend = new TextView(this);
            currentSpend.setText("Current spend: £"+(int)tempBudget.getCurrentSpend()+"\nLimit: £"+tempBudget.getLimit());
            currentSpend.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            currentSpend.setPadding(0, 0, 0, 10);
            containerBudgets.addView(currentSpend);


        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    public static ArrayList<String[]> getTempFalseData(){



        String[][] source = {{"Utilities","100","02/03/15","02/04/15","100"}
                ,{"Fuel","30","05/03/15","05/04/15","25"}
                ,{"Food","70","08/03/15","08/04/15","35"}
                ,{"Personal","40","04/03/15","04/04/15","5"},{"Personel","40","04/03/15","04/04/15","5"}};


        ArrayList<String[]> budgetData = new ArrayList<String[]>();

        for ( int i = 0 ; i< source.length; i++) budgetData.add(source[i]);

        return budgetData;

    }





}