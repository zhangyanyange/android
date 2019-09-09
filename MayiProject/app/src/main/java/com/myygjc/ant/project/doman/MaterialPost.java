package com.myygjc.ant.project.doman;

import java.util.List;

/**
 * Created by zy2 on 2017/2/20.
 */

public class MaterialPost {


    /**
     * jhjcsj : 2017-02-20T09:41:46.0703474+08:00
     * shdz : sample string 2
     * bz : sample string 3
     * sfzq : true
     * ecdyjl : 5.0
     * ltcs : 6
     * dtlcs : 7
     * lxr : sample string 8
     * lxdh : sample string 9
     * khhsid : 10
     * sffk : true
     * tldmx : [{"ID":1,"Qty":2,"price":3},{"ID":1,"Qty":2,"price":3}]
     */

    private String jhjcsj;
    private String shdz;
    private String bz;
    private boolean sfzq;
    private double ecdyjl;
    private int ltcs;
    private int dtlcs;
    private String lxr;
    private String lxdh;
    private boolean sfxyby;

    public boolean isSfxyby() {
        return sfxyby;
    }

    public void setSfxyby(boolean sfxyby) {
        this.sfxyby = sfxyby;
    }

    private String khhsid;

    private List<TldmxBean> tldmx;

    public String getJhjcsj() {
        return jhjcsj;
    }

    public void setJhjcsj(String jhjcsj) {
        this.jhjcsj = jhjcsj;
    }

    public String getShdz() {
        return shdz;
    }

    public void setShdz(String shdz) {
        this.shdz = shdz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public boolean isSfzq() {
        return sfzq;
    }

    public void setSfzq(boolean sfzq) {
        this.sfzq = sfzq;
    }

    public double getEcdyjl() {
        return ecdyjl;
    }

    public void setEcdyjl(double ecdyjl) {
        this.ecdyjl = ecdyjl;
    }

    public int getLtcs() {
        return ltcs;
    }

    public void setLtcs(int ltcs) {
        this.ltcs = ltcs;
    }

    public int getDtlcs() {
        return dtlcs;
    }

    public void setDtlcs(int dtlcs) {
        this.dtlcs = dtlcs;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getKhhsid() {
        return khhsid;
    }

    public void setKhhsid(String khhsid) {
        this.khhsid = khhsid;
    }



    public List<TldmxBean> getTldmx() {
        return tldmx;
    }

    public void setTldmx(List<TldmxBean> tldmx) {
        this.tldmx = tldmx;
    }

    public static class TldmxBean {
        /**
         * ID : 1
         * Qty : 2.0
         * price : 3.0
         */

        private int ID;
        private double Qty;
        private double price;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public double getQty() {
            return Qty;
        }

        public void setQty(double Qty) {
            this.Qty = Qty;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
