package com.microfeel.meiquetiliao.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy2 on 2017/12/4.
 */

public class GetProjectList implements Parcelable {

    /**
     * ID : fa1130a5-0c37-4ea7-b014-1698853507cf
     * CustomerNo : Q17-3686
     * CustomerName : 霍先生
     * Sex : true
     * Phone : 15104585567,
     * Mobile : null
     * Address : 万达城B7-1-2504
     * Area : 80.0
     * Height : 2.8
     * BuildType : null
     * HouseType : null
     * Statue : 5
     * CreateTime : 2017-06-07T12:00:51.783
     * ContractTime : 2017-06-20T17:33:05
     * OrgID : 02c39a0f-2c14-4d4f-b7a9-441aecb2f570
     * HouseType2 : 高层
     * Remark : 黄敬文
     */

    private String ID;
    private String CustomerNo;
    private String CustomerName;
    private boolean Sex;
    private String Phone;
    private String Mobile;
    private String Address;
    private double Area;
    private double Height;
    private String BuildType;
    private String HouseType;
    private int Statue;
    private String CreateTime;
    private String ContractTime;
    private String OrgID;
    private String HouseType2;
    private String Remark;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public double getArea() {
        return Area;
    }

    public void setArea(double area) {
        Area = area;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public String getBuildType() {
        return BuildType;
    }

    public void setBuildType(String buildType) {
        BuildType = buildType;
    }

    public String getHouseType() {
        return HouseType;
    }

    public void setHouseType(String houseType) {
        HouseType = houseType;
    }

    public int getStatue() {
        return Statue;
    }

    public void setStatue(int statue) {
        Statue = statue;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getContractTime() {
        return ContractTime;
    }

    public void setContractTime(String contractTime) {
        ContractTime = contractTime;
    }

    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        OrgID = orgID;
    }

    public String getHouseType2() {
        return HouseType2;
    }

    public void setHouseType2(String houseType2) {
        HouseType2 = houseType2;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.CustomerNo);
        dest.writeString(this.CustomerName);
        dest.writeByte(this.Sex ? (byte) 1 : (byte) 0);
        dest.writeString(this.Phone);
        dest.writeString(this.Mobile);
        dest.writeString(this.Address);
        dest.writeDouble(this.Area);
        dest.writeDouble(this.Height);
        dest.writeString(this.BuildType);
        dest.writeString(this.HouseType);
        dest.writeInt(this.Statue);
        dest.writeString(this.CreateTime);
        dest.writeString(this.ContractTime);
        dest.writeString(this.OrgID);
        dest.writeString(this.HouseType2);
        dest.writeString(this.Remark);
    }

    public GetProjectList() {
    }

    protected GetProjectList(Parcel in) {
        this.ID = in.readString();
        this.CustomerNo = in.readString();
        this.CustomerName = in.readString();
        this.Sex = in.readByte() != 0;
        this.Phone = in.readString();
        this.Mobile = in.readString();
        this.Address = in.readString();
        this.Area = in.readDouble();
        this.Height = in.readDouble();
        this.BuildType = in.readString();
        this.HouseType = in.readString();
        this.Statue = in.readInt();
        this.CreateTime = in.readString();
        this.ContractTime = in.readString();
        this.OrgID = in.readString();
        this.HouseType2 = in.readString();
        this.Remark = in.readString();
    }

    public static final Parcelable.Creator<GetProjectList> CREATOR = new Parcelable.Creator<GetProjectList>() {
        @Override
        public GetProjectList createFromParcel(Parcel source) {
            return new GetProjectList(source);
        }

        @Override
        public GetProjectList[] newArray(int size) {
            return new GetProjectList[size];
        }
    };
}
