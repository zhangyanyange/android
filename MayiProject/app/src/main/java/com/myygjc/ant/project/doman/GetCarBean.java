package com.myygjc.ant.project.doman;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy2 on 2017/2/21.
 */
public class GetCarBean implements Parcelable {

    /**
     * je : 1.0
     * unit : sample string 2
     * wlId : sample string 3
     * name : sample string 4
     * spec : sample string 5
     * weight : 6.0
     * ID : 2d91662c-4206-4127-9ed3-86a09e1dddcb
     * sl : 8.0
     * bz : sample string 9
     * scsj : 2017-02-21T17:13:02.6016005+08:00
     * dj : 11.0
     * sjsl : 12.0
     */

    private double je;
    private String unit;
    private String wlId;
    private String name;
    private String spec;
    private double weight;
    private String ID;
    private double sl;
    private String bz;
    private String scsj;
    private double dj;
    private double sjsl;

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWlId() {
        return wlId;
    }

    public void setWlId(String wlId) {
        this.wlId = wlId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public double getDj() {
        return dj;
    }

    public void setDj(double dj) {
        this.dj = dj;
    }

    public double getSjsl() {
        return sjsl;
    }

    public void setSjsl(double sjsl) {
        this.sjsl = sjsl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.je);
        dest.writeString(this.unit);
        dest.writeString(this.wlId);
        dest.writeString(this.name);
        dest.writeString(this.spec);
        dest.writeDouble(this.weight);
        dest.writeString(this.ID);
        dest.writeDouble(this.sl);
        dest.writeString(this.bz);
        dest.writeString(this.scsj);
        dest.writeDouble(this.dj);
        dest.writeDouble(this.sjsl);
    }

    public GetCarBean() {
    }

    protected GetCarBean(Parcel in) {
        this.je = in.readDouble();
        this.unit = in.readString();
        this.wlId = in.readString();
        this.name = in.readString();
        this.spec = in.readString();
        this.weight = in.readDouble();
        this.ID = in.readString();
        this.sl = in.readDouble();
        this.bz = in.readString();
        this.scsj = in.readString();
        this.dj = in.readDouble();
        this.sjsl = in.readDouble();
    }

    public static final Parcelable.Creator<GetCarBean> CREATOR = new Parcelable.Creator<GetCarBean>() {
        @Override
        public GetCarBean createFromParcel(Parcel source) {
            return new GetCarBean(source);
        }

        @Override
        public GetCarBean[] newArray(int size) {
            return new GetCarBean[size];
        }
    };
}
