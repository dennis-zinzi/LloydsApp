package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis on 7/3/15.
 */

public class Budget {
    private String name;
    private double balance;
    private double limit;
    //Array of Types Budget can be, name will take in one of these values
    //If specify name, text box appears where user inputs name of budget
    private String[] types = {"Specify name...","House","Bills","Car","Education","Electricity","Fuel","Gas","Groceries",
            "Health","Internet","Personal","Pets","Phone","Restaurant","Travel","Water","Other"};


    public Budget(String name, double limit){
        this.name = name;
        this.limit = limit;
        balance = 0;
    }

    public void addToBudget(double amount){
        limit += amount;
    }

    public void addExpense(double amount){
        if(balance+amount == limit){
            System.out.println("Limit of "+name+ "Reached");
            balance = limit;
        }
        else if(balance+amount >= limit){
            System.out.println("Limit of "+name+" Surpassed");
            //Throw error like change bar red or something
            balance = limit;
        }
        else{
            balance += amount;
        }
    }


    public String getName(){
        return name;
    }

    public double getBalance(){
        return balance;
    }

    public double getLimit(){
        return limit;
    }

    public String[] getTypes(){
        return types;
    }
}