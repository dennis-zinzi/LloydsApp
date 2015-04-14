package uk.ac.ncl.csc2022.team10.lloydsapp;



        import java.util.ArrayList;

        import android.app.ActionBar;
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
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.LinearLayout.LayoutParams;
        import android.widget.ProgressBar;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;


public class BudgetActivity extends Activity {
    ArrayList<String[]> budgets;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Budgeting");


        budgets = getData();

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

        container.addView(addBudget);

        for(int i=1; i<budgets.size()+1; i++){

            String[] tempBudget = budgets.get(i-1);


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
                    return false; //Why does it need this?

                }
            });

            container.addView(containerBudgets,paramContainerBudgets);

            TextView budgetName = new TextView(this);
            budgetName.setText(tempBudget[0]);
            budgetName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            containerBudgets.addView(budgetName);

            LinearLayout containerBudgetInfo = new LinearLayout(this);
            containerBudgetInfo.setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams paramContainerBudgetInfo = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            containerBudgets.addView(containerBudgetInfo,paramContainerBudgetInfo);

            ProgressBar Pb = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
            Pb.setProgress(((int)((Double.valueOf(tempBudget[4])/Double.valueOf(tempBudget[1]))*100)));
            Pb.setPadding(10, 0, 10, 0);
            containerBudgetInfo.addView(Pb,paramContainerBudgetInfo);

            TextView currentSpend = new TextView(this);
            currentSpend.setText("Current spend: £"+tempBudget[4]+"\nLimit: £"+tempBudget[1]);
            currentSpend.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            currentSpend.setPadding(0, 0, 0, 10);
            containerBudgets.addView(currentSpend,paramContainerBudgetInfo);



			/*Button AddToCurrent = new Button(this);
			AddToCurrent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			AddToCurrent.setText("Edit");
			AddToCurrent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			AddToCurrent.setGravity(Gravity.CENTER);
			containerBudgets.addView(AddToCurrent);*/



        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    public static ArrayList<String[]> getData(){



        String[][] source = {{"Utilities","100","02/03/15","02/04/15","100"}
                ,{"Fuel","30","05/03/15","05/04/15","25"}
                ,{"Food","70","08/03/15","08/04/15","35"}
                ,{"Personal","40","04/03/15","04/04/15","5"},{"Personel","40","04/03/15","04/04/15","5"}};


        ArrayList<String[]> budgetData = new ArrayList<String[]>();

        for ( int i = 0 ; i< source.length; i++) budgetData.add(source[i]);

        return budgetData;

    }





}