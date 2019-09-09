package com.mvc.microfeel.domain;

/**
 * Created by zy2 on 2017/9/4.
 */

public class ExInAllProject {
    private int AccountID;
    private String AccountName;
    private double Amount;

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }
}
