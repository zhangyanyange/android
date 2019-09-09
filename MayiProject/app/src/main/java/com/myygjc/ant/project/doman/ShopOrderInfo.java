package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/2/27.
 */

public class ShopOrderInfo {

    /**
     * state : 1
     * dh : MY20170227030406
     * spyf : 0.0
     * czyf : 0.0
     */

    private int state;
    private String dh;
    private double spyf;
    private double czyf;
    private double dyyf;

    public double getDyyf() {
        return dyyf;
    }

    public void setDyyf(double dyyf) {
        this.dyyf = dyyf;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public double getSpyf() {
        return spyf;
    }

    public void setSpyf(double spyf) {
        this.spyf = spyf;
    }

    public double getCzyf() {
        return czyf;
    }

    public void setCzyf(double czyf) {
        this.czyf = czyf;
    }
}
