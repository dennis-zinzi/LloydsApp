package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis on 7/3/15.
 * Edited by Rhys Covell
 */

public class Budget {
    private String name;
    private double current;
    private double limit;
    //Array of Types Budget can be, name will take in one of these values
    //If specify name, text box appears where user inputs name of budget
    private String[] types = {"Specify name...","House","Bills","Car","Education","Electricity","Fuel","Gas","Groceries",
            "Health","Internet","Personal","Pets","Phone","Restaurant","Travel","Water","Other"};


    public Budget(String name, double limit){
        this.name = name;
        this.limit = limit;
        current = 0;
    }



    public void addExpense(double amount){
        if(current+amount == limit){
            System.out.println("Limit of "+name+ "Reached");
            current = limit;
        }
        else if(current+amount >= limit){
            System.out.println("Limit of "+name+" Surpassed");
            //Throw error like change bar red or something
            current = limit;
        }
        else{
            current += amount;
        }
    }


    public String getName(){
        return name;
    }

    public void setName(String newName){ name = newName;}

    public double getCurrentSpend(){ return current; }

    public void setCurrentSpend(Double spend){ current = spend; }

    public double getLimit(){
        return limit;
    }

    public void setLimit(Double newLim){ limit = newLim;}

    public String[] getTypes(){
        return types;
    }
}