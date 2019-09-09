package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/2/16.
 */

public class ShopDetail {
    private int shopid;
    private String name;
    private String price;
    private int count;
    private double shopprice;

    public double getShopprice() {
        return shopprice;
    }

    public void setShopprice(double shopprice) {
        this.shopprice = shopprice;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
