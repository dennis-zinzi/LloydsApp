package uk.ac.ncl.csc2022.team10.lloydsapp;

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
    private User u;

    public static BalanceFragment newInstance(int sectionNumber) {
        BalanceFragment fragment = new BalanceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public BalanceFragment() {
        a = new Account(25,1096.67,2500.00);
        u = new User("DZ",30);
        u.addBudget(new Budget("Personal",100));
        u.addBudget(new Budget("Groceries",200));
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

        balanceLabel.setText(a.getBalance()+"");
        overdraftLabel.setText(a.getOverdraftLimit()+"");

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
                // TODO Auto-generated method stub
                if(budgetLabel1.getText()!=""){
                    budgetLabel1.setText("");
                }
            }
        });

        //addToBudget -> ask which to add to, then make appropriate change on progress bar

        //manageBudgets -> Budget manager screen (New Budget, Modify Budget, Delete Budget, Remove from Budget)



        return rootView;
    }
    //	@Override
    //	public void onAttach(Activity activity) {
    //		super.onAttach(activity);
    //		((MainActivity) activity).onSectionAttached(getArguments().getInt(
    //				ARG_SECTION_NUMBER));
    //	}
}