package com.mvc.microfeel.domain;

/**
 * Created by zy2 on 2017/9/1.
 */


public class ExInProject {

    /**
     * AccountID : 1040
     * AccountName : 家装
     * Amount : 61451.0
     * Date : 2017/3/16
     * Explanation : 家装：张瑞环首期款-夏忠清Q17-1824(合同额80240元）

     * FDC : 0
     * FDetailID : 6418
     * Preparer : 张爽
     */

    private int AccountID;
    private String AccountName;
    private double Amount;
    private String Date;
    private String Explanation;
    private int FDC;
    private int FDetailID;
    private String Preparer;

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int AccountID) {
        this.AccountID = AccountID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String Explanation) {
        this.Explanation = Explanation;
    }

    public int getFDC() {
        return FDC;
    }

    public void setFDC(int FDC) {
        this.FDC = FDC;
    }

    public int getFDetailID() {
        return FDetailID;
    }

    public void setFDetailID(int FDetailID) {
        this.FDetailID = FDetailID;
    }

    public String getPreparer() {
        return Preparer;
    }

    public void setPreparer(String Preparer) {
        this.Preparer = Preparer;
    }
}
