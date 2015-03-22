package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis on 7/3/15.
 */

public class Wallet {
    private String name;
    private int balancePoints;
    private double balanceMoney;
    //Can't think of other fields

    public Wallet(String name, int balancePoints){
        this.name = name;
        this.balancePoints = balancePoints;
    }

    public Wallet(String name, double balanceMoney){
        this.name = name;
        this.balanceMoney = balanceMoney;
    }
}