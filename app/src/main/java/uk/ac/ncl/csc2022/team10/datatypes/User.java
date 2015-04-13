package uk.ac.ncl.csc2022.team10.datatypes;

import java.util.*;

public class User {

    private String name;
    private String userID;
    private String birthday;
    private String address;
    private String password;
    private List<Account> accounts;
    private List<Budget> budgets;
    private List<Contact> contacts;
    private List<Wallet> wallets;


    public User(String name, String userID){
        this.name = name;
        this.userID = userID;
        budgets = new ArrayList<Budget>();
        contacts = new ArrayList<Contact>();
        accounts = new ArrayList<Account>();
    }

    public void addBudget(Budget b){
        budgets.add(b);
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


    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Budget> getBudgets(){
        return budgets;
    }

    public List<Contact> getContacts(){
        return contacts;
    }

    public void addContact(Contact c){
        contacts.add(c);
    }

    public void addAccount(Account a){
        accounts.add(a);
    }

}
