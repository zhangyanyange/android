package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/2/22.
 */

public class AddMaterialCar {

    /**
     * HSID : sample string 1
     * wlID : 2
     * qty : 3.0
     * price : 4.0
     */

    private String HSID;
    private int wlID;
    private double qty;
    private double price;

    public String getHSID() {
        return HSID;
    }

    public void setHSID(String HSID) {
        this.HSID = HSID;
    }

    public int getWlID() {
        return wlID;
    }

    public void setWlID(int wlID) {
        this.wlID = wlID;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
