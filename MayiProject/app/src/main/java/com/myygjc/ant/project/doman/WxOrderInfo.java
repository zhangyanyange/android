package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/2/28.
 */

public class WxOrderInfo {

    /**
     * state : 1
     * yddh : wx201702280926272d24d5ce7f0019981219
     * qm : 59E0EE2FA7B090E86460B08F10A60A9B
     * sjzfc : 6db2d4863f2942bea8ec5c94e0489725
     * sjc : 1488245186
     */

    private int state;
    private String yddh;
    private String qm;
    private String sjzfc;
    private String sjc;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getYddh() {
        return yddh;
    }

    public void setYddh(String yddh) {
        this.yddh = yddh;
    }

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public String getSjzfc() {
        return sjzfc;
    }

    public void setSjzfc(String sjzfc) {
        this.sjzfc = sjzfc;
    }

    public String getSjc() {
        return sjc;
    }

    public void setSjc(String sjc) {
        this.sjc = sjc;
    }
}
