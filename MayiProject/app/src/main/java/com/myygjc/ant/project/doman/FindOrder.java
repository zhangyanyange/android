package com.myygjc.ant.project.doman;

import java.util.List;

/**
 * Created by zy2 on 2017/3/6.
 */

public class FindOrder {


    /**
     * ID : 5943de36-fdce-4358-90d6-a2f8bd1732e8
     * czyf : 0.0
     * dtcs : 0
     * ecdyjl : 100.0
     * ecdyyf : 0.0
     * jd : 0.0
     * je : 130.0
     * jhjcsj : 2017-03-28T00:00:00
     * khid : 5682
     * ltcs : 100
     * scsj : 2017-03-28T14:20:10.377
     * sffk : false
     * sfzq : false
     * shdz : 微感测试
     * sjjcsj : 0001-01-01T00:00:00
     * spyf : 0.0
     * tldmx : [{"ID":"f008f3dd-74fa-4f96-bfc2-5e1dc23a6c2c","dj":65,"scsj":"2017-03-28T14:20:10.377","sjsl":2,"sl":2,"物料ID":2476}]
     * wd : 0.0
     * ywdjh : MY20170328022010
     * zffs : 0
     * zl : 0.0
     * zt : 6
     */

    private String ID;
    private double czyf;
    private int dtcs;
    private double ecdyjl;
    private double ecdyyf;
    private double jd;
    private double je;
    private String jhjcsj;
    private int khid;
    private int ltcs;
    private String scsj;
    private boolean sffk;
    private boolean sfzq;
    private String shdz;
    private String sjjcsj;
    private double spyf;
    private double wd;
    private String ywdjh;
    private int zffs;
    private double zl;
    private int zt;
    private List<TldmxBean> tldmx;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCzyf() {
        return czyf;
    }

    public void setCzyf(double czyf) {
        this.czyf = czyf;
    }

    public int getDtcs() {
        return dtcs;
    }

    public void setDtcs(int dtcs) {
        this.dtcs = dtcs;
    }

    public double getEcdyjl() {
        return ecdyjl;
    }

    public void setEcdyjl(double ecdyjl) {
        this.ecdyjl = ecdyjl;
    }

    public double getEcdyyf() {
        return ecdyyf;
    }

    public void setEcdyyf(double ecdyyf) {
        this.ecdyyf = ecdyyf;
    }

    public double getJd() {
        return jd;
    }

    public void setJd(double jd) {
        this.jd = jd;
    }

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public String getJhjcsj() {
        return jhjcsj;
    }

    public void setJhjcsj(String jhjcsj) {
        this.jhjcsj = jhjcsj;
    }

    public int getKhid() {
        return khid;
    }

    public void setKhid(int khid) {
        this.khid = khid;
    }

    public int getLtcs() {
        return ltcs;
    }

    public void setLtcs(int ltcs) {
        this.ltcs = ltcs;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public boolean isSffk() {
        return sffk;
    }

    public void setSffk(boolean sffk) {
        this.sffk = sffk;
    }

    public boolean isSfzq() {
        return sfzq;
    }

    public void setSfzq(boolean sfzq) {
        this.sfzq = sfzq;
    }

    public String getShdz() {
        return shdz;
    }

    public void setShdz(String shdz) {
        this.shdz = shdz;
    }

    public String getSjjcsj() {
        return sjjcsj;
    }

    public void setSjjcsj(String sjjcsj) {
        this.sjjcsj = sjjcsj;
    }

    public double getSpyf() {
        return spyf;
    }

    public void setSpyf(double spyf) {
        this.spyf = spyf;
    }

    public double getWd() {
        return wd;
    }

    public void setWd(double wd) {
        this.wd = wd;
    }

    public String getYwdjh() {
        return ywdjh;
    }

    public void setYwdjh(String ywdjh) {
        this.ywdjh = ywdjh;
    }

    public int getZffs() {
        return zffs;
    }

    public void setZffs(int zffs) {
        this.zffs = zffs;
    }

    public double getZl() {
        return zl;
    }

    public void setZl(double zl) {
        this.zl = zl;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public List<TldmxBean> getTldmx() {
        return tldmx;
    }

    public void setTldmx(List<TldmxBean> tldmx) {
        this.tldmx = tldmx;
    }

    public static class TldmxBean {
        /**
         * ID : f008f3dd-74fa-4f96-bfc2-5e1dc23a6c2c
         * dj : 65.0
         * scsj : 2017-03-28T14:20:10.377
         * sjsl : 2.0
         * sl : 2.0
         * 物料ID : 2476
         */

        private String ID;
        private double dj;
        private String scsj;
        private double sjsl;
        private double sl;
        private int 物料ID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public double getDj() {
            return dj;
        }

        public void setDj(double dj) {
            this.dj = dj;
        }

        public String getScsj() {
            return scsj;
        }

        public void setScsj(String scsj) {
            this.scsj = scsj;
        }

        public double getSjsl() {
            return sjsl;
        }

        public void setSjsl(double sjsl) {
            this.sjsl = sjsl;
        }

        public double getSl() {
            return sl;
        }

        public void setSl(double sl) {
            this.sl = sl;
        }

        public int get物料ID() {
            return 物料ID;
        }

        public void set物料ID(int 物料ID) {
            this.物料ID = 物料ID;
        }
    }
}
