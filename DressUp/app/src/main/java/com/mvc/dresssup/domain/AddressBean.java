package com.mvc.dresssup.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy2 on 2017/10/25.
 */

public class AddressBean implements Parcelable {

    /**
     * Id : sample string 1
     * Address : sample string 2
     * Telephone : sample string 3
     * IsDefault : true
     * Consignee : sample string 5
     */

    private String Id;
    private String Address;
    private String Telephone;
    private int IsDefault;
    private String Consignee;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public int isIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String Consignee) {
        this.Consignee = Consignee;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Address);
        dest.writeString(this.Telephone);
        dest.writeInt(this.IsDefault);
        dest.writeString(this.Consignee);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.Id = in.readString();
        this.Address = in.readString();
        this.Telephone = in.readString();
        this.IsDefault = in.readInt();
        this.Consignee = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
