package com.mvc.microfeel.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zy2 on 2017/8/31.
 */
public class ProjectList implements Parcelable{

    /**
     * ID : a5f632cc-808d-e711-8118-00155d58e105
     * Adress : 国际花都B2-3-502
     * NO : Q17-2737
     * ProjectName : null
     * CustomerName : 马姐
     * CustomerPhone : 18504608951
     */
    private String CreateTime;
    private String ID;
    private String Adress;
    private String NO;
    private String ProjectName;
    private String CustomerName;
    private String CustomerPhone;
    private String ProjectType;

    protected ProjectList(Parcel in) {
        CreateTime = in.readString();
        ID = in.readString();
        Adress = in.readString();
        NO = in.readString();
        ProjectName = in.readString();
        CustomerName = in.readString();
        CustomerPhone = in.readString();
        ProjectType = in.readString();
    }

    public static final Creator<ProjectList> CREATOR = new Creator<ProjectList>() {
        @Override
        public ProjectList createFromParcel(Parcel in) {
            return new ProjectList(in);
        }

        @Override
        public ProjectList[] newArray(int size) {
            return new ProjectList[size];
        }
    };

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String projectType) {
        ProjectType = projectType;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String CustomerPhone) {
        this.CustomerPhone = CustomerPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CreateTime);
        parcel.writeString(ID);
        parcel.writeString(Adress);
        parcel.writeString(NO);
        parcel.writeString(ProjectName);
        parcel.writeString(CustomerName);
        parcel.writeString(CustomerPhone);
        parcel.writeString(ProjectType);
    }
}
