package uk.ac.ncl.csc2022.team10.lloydsapp;

import uk.ac.ncl.csc2022.team10.budgeting.BudgetActivity;
import uk.ac.ncl.csc2022.team10.datatypes.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import android.widget.TextView;

public class BalanceFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RelativeLayout myLinearLayout;
    private TextView budgetLabel1;

    private TextView balanceLabel;
    private TextView overdraftLabel;
    private Button addToBudget;
    private Button manageBudgets;
    private Account a;
    private User user;

    public static BalanceFragment newInstance(int sectionNumber) {
        BalanceFragment fragment = new BalanceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public BalanceFragment() {
        //user = MainActivity.getUser();
        user = MainActivity.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_balance, container,
                false);




        balanceLabel = (TextView)rootView.findViewById(R.id.balanceText);
        overdraftLabel = (TextView)rootView.findViewById(R.id.overdraftText);
        addToBudget = (Button)rootView.findViewById(R.id.addToBudget);
        manageBudgets = (Button)rootView.findViewById(R.id.budgetManage);

        balanceLabel.setText(String.format("%.2f",user.getAccounts().get(0).getBalance()));
        overdraftLabel.setText(String.format("%.2f",user.getAccounts().get(0).getOverdraftLimit()));

        //Just code to learn/test creation of xml elements dynamically, will change it appropriately later
        addToBudget.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                myLinearLayout = (RelativeLayout) rootView.findViewById(R.id.balanceLayout);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                budgetLabel1 = new TextView(getActivity());
                if(budgetLabel1.getText()==""){
                    budgetLabel1.setText("IT WORKS!!!!!");
                }

                budgetLabel1.setLayoutParams(params);
                myLinearLayout.addView(budgetLabel1);


            }

        });

        manageBudgets.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),BudgetActivity.class);
                startActivity(intent);
            }
        });

        //addToBudget -> ask which to add to, then make appropriate change on progress bar

        //manageBudgets -> Budget manager screen (New Budget, Modify Budget, Delete Budget, Remove from Budget)



        return rootView;
    }

}