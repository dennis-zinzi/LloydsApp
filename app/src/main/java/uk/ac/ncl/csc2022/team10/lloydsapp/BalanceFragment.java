package uk.ac.ncl.csc2022.team10.lloydsapp;

import uk.ac.ncl.csc2022.team10.datatypes.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class BalanceFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RelativeLayout myLinearLayout;
    private LinearLayout budgetLin;
    private TextView budgetLabel1;
    private TextView balanceLabel;
    private TextView overdraftLabel;
    private Button manageBudgets;
    private Account a;
    private User user;
    private List<Budget> budgets;

    public static BalanceFragment newInstance(int sectionNumber) {
        BalanceFragment fragment = new BalanceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public BalanceFragment() {user = MainActivity.getUser(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_balance, container,
                false);


        balanceLabel = (TextView) rootView.findViewById(R.id.balanceText);
        overdraftLabel = (TextView) rootView.findViewById(R.id.overdraftText);
        budgetLin = (LinearLayout) rootView.findViewById(R.id.exteriorBudgets);
        manageBudgets = (Button) rootView.findViewById(R.id.budgetManage);

        balanceLabel.setText(String.format("%.2f", user.getAccount().getBalance()));
        overdraftLabel.setText(String.format("%.2f", user.getAccount().getOverdraftLimit()));


        manageBudgets.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BudgetActivity.class);
                startActivity(intent);
            }
        });


        budgets = user.getBudgets();


        previewBudgets();

        return rootView;
    }




private void previewBudgets(){

    LayoutInflater getPreview = LayoutInflater.from(getActivity());
    LinearLayout preview = (LinearLayout) getPreview.inflate(R.layout.budget_preview, null, false);
    budgetLin.addView(preview);

    TextView namePreview = (TextView) preview.findViewById(R.id.budgetNamePreview);
    namePreview.setText("Overall");

    ProgressBar pgPreview = (ProgressBar) preview.findViewById(R.id.budgetPbPreview);
    TextView summaryPrev = (TextView) preview.findViewById(R.id.budgetInfoPreview);


    int netProfOrLoss=0;
    int sumLimits=0;
    int sumCurrentSpend=0;
    for (int i = 0; i < budgets.size(); i++) {
        sumLimits += ((int) (budgets.get(i).getLimit()));
        sumCurrentSpend+= ((int) budgets.get(i).getCurrentSpend());
        netProfOrLoss+=sumLimits-sumCurrentSpend;
    }

    try {
        pgPreview.setProgress(sumCurrentSpend * 100 / sumLimits);
    }catch (ArithmeticException e){
        pgPreview.setProgress(0);
        pgPreview.setContentDescription("No Budgets");

    }

    if (netProfOrLoss<0){
        summaryPrev.setText("Spent -"+Math.abs(netProfOrLoss));
    }else
    {
        summaryPrev.setText("Saved +"+netProfOrLoss);
    }
    }
}



