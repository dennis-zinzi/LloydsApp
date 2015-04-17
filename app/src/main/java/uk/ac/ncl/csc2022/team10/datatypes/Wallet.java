package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis on 7/3/15.
 */
/*
    Modified by author: szholdiyarov
 */


public class Wallet {
    private String name;
    private double balance;

    public Wallet(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        balance = balance + newBalance;
    }

}