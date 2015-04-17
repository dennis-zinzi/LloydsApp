package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis Zinzi on 7/3/15.
 */

public class Contact {

    private String name;
    private Account account;

    public Contact(String name, Account account) {
        this.name = name;
        this.account = account;
    }


    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}
