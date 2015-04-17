package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis Zinzi on 7/3/15.
 */
/*
    Modified by: Sanzhar Zholdiyarov
 */

import java.util.*;

public class Account {

    private int accountID;
    private double balance;
    private double overdraftLimit;
    private List<Transaction> transactionList;

    public Account(int accountID, double balance, double overdraftLimit) {
        this.accountID = accountID;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
        transactionList = new ArrayList<Transaction>();
    }

    public void addBalance(double moneyIn) {
        balance += moneyIn;
    }

    public void removeBalance(double moneyOut) {
        balance -= moneyOut;
    }

    public void transferFund(double amount, Account to) {
        removeBalance(amount);
        to.addBalance(amount);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + month + "/" + year;
        transactionList.add(new Transaction(amount, date, this, to));
    }

    public double getBalance() {
        return balance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public int getAccountID() {
        return accountID;
    }

    public List<Transaction> getTransaction() {
        return transactionList;
    }
}