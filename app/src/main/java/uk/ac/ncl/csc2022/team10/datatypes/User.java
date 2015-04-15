package uk.ac.ncl.csc2022.team10.datatypes;

import android.graphics.Point;
import android.util.Log;

import java.util.*;

public class User {

    private String name;
    private String userID;
    private String birthday;
    private String address;
    private String password;
    private Account account;
    private List<Budget> budgets;
    private List<Contact> contacts;
    private List<Wallet> wallets;
    private List<Points> points;


    public User(String name, String userID, Account account) {
        this.name = name;
        this.userID = userID;
        budgets = new ArrayList<Budget>();
        this.account = account;
        contacts = new ArrayList<Contact>();
        wallets = new ArrayList<Wallet>();
        points = new ArrayList<Points>();
    }

    public void addBudget(Budget b) {
        budgets.add(b);
    }

    public void addWallet(Wallet w) {
        wallets.add(w);
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(Account a) {
        account = a;
    }

    public Account getAccount() {
        return account;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact c) {
        contacts.add(c);
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public Wallet getWalletByName(String name) {
        Log.i("Wallets", "Start working with " + name);
        for (int i = 0; i < wallets.size(); i++) {
            Log.i("Wallets", "for w : " + wallets.get(i).getName());
            if (wallets.get(i).getName().equals(name)) {
                Log.i("Wallets", "return  " + wallets.get(i));
                return wallets.get(i);
            }
        }
        return null;
    }

    public void addPoint(Points p) {
        points.add(p);
    }

    public List<Points> getPoints(){
        return points;
    }

    //public void addAccount(Account a){
    //  accounts.add(a);
    //}

}
