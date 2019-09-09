package com.microfeel.meiquetiliao.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/12/6.
 */

public class MaterialPostBean {
    /**
     * ID : 954ac381-fdcf-4d3a-ab44-2f73a5444819
     * ProjectID : a6bca919-09c5-47d7-b571-1baa768bf5de
     * Code : sample string 3
     * CreaterID : e0ff3404-2277-4828-b80f-a8b26858612b
     * LiftNum : 5
     * FloorNum : 6
     * ShipDistance : 1.0
     * ShippingCost : 7.0
     * HShipCost : 8.0
     * VShipCost : 9.0
     * IsSelfPick : true
     * CreateTime : 2017-12-06T10:07:13.4364479+08:00
     * SubmitTime : 2017-12-06T10:07:13.4364479+08:00
     * CallBackTime : 2017-12-06T10:07:13.4364479+08:00
     * PostTime : 2017-12-06T10:07:13.4364479+08:00
     * Remark : sample string 12
     * RequireTime : 2017-12-06T10:07:13.4364479+08:00
     * Receiver : sample string 14
     * Contact : sample string 15
     * Address : sample string 16
     * OrderDetail : [{"ID":"6c13db48-b9b7-40b0-b0b7-db6ca4b3a9b0","OrderID":"954ac381-fdcf-4d3a-ab44-2f73a5444819","MaterialID":3,"Name":"sample string 4","Number":5,"Price":6,"Unit":"sample string 7","ActualNumber":1,"Remark":"sample string 8","Standard":"sample string 9","Brand":"sample string 10"}]
     */

    private String ID;
    private String ProjectID;
    private String Code;
    private String CreaterID;
    private int LiftNum;
    private int FloorNum;
    private double ShipDistance;
    private double ShippingCost;
    private double HShipCost;
    private double VShipCost;
    private boolean IsSelfPick;
    private String CreateTime;
    private String SubmitTime;
    private String CallBackTime;
    private String PostTime;
    private String Remark;
    private String RequireTime;
    private String Receiver;
    private String Contact;
    private String Address;
    private List<OrderDetailBean> OrderDetail;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getCreaterID() {
        return CreaterID;
    }

    public void setCreaterID(String CreaterID) {
        this.CreaterID = CreaterID;
    }

    public int getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(int LiftNum) {
        this.LiftNum = LiftNum;
    }

    public int getFloorNum() {
        return FloorNum;
    }

    public void setFloorNum(int FloorNum) {
        this.FloorNum = FloorNum;
    }

    public double getShipDistance() {
        return ShipDistance;
    }

    public void setShipDistance(double ShipDistance) {
        this.ShipDistance = ShipDistance;
    }

    public double getShippingCost() {
        return ShippingCost;
    }

    public void setShippingCost(double ShippingCost) {
        this.ShippingCost = ShippingCost;
    }

    public double getHShipCost() {
        return HShipCost;
    }

    public void setHShipCost(double HShipCost) {
        this.HShipCost = HShipCost;
    }

    public double getVShipCost() {
        return VShipCost;
    }

    public void setVShipCost(double VShipCost) {
        this.VShipCost = VShipCost;
    }

    public boolean isIsSelfPick() {
        return IsSelfPick;
    }

    public void setIsSelfPick(boolean IsSelfPick) {
        this.IsSelfPick = IsSelfPick;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String SubmitTime) {
        this.SubmitTime = SubmitTime;
    }

    public String getCallBackTime() {
        return CallBackTime;
    }

    public void setCallBackTime(String CallBackTime) {
        this.CallBackTime = CallBackTime;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String PostTime) {
        this.PostTime = PostTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRequireTime() {
        return RequireTime;
    }

    public void setRequireTime(String RequireTime) {
        this.RequireTime = RequireTime;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> OrderDetail) {
        this.OrderDetail = OrderDetail;
    }


    public static class OrderDetailBean {
        /**
         * ID : 6c13db48-b9b7-40b0-b0b7-db6ca4b3a9b0
         * OrderID : 954ac381-fdcf-4d3a-ab44-2f73a5444819
         * MaterialID : 3
         * Name : sample string 4
         * Number : 5.0
         * Price : 6.0
         * Unit : sample string 7
         * ActualNumber : 1.0
         * Remark : sample string 8
         * Standard : sample string 9
         * Brand : sample string 10
         */

        private String ID;
        private int MaterialID;
        private String Name;
        private double Number;
        private double Price;
        private String Unit;
        private double ActualNumber;


        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }


        public int getMaterialID() {
            return MaterialID;
        }

        public void setMaterialID(int MaterialID) {
            this.MaterialID = MaterialID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public double getNumber() {
            return Number;
        }

        public void setNumber(double Number) {
            this.Number = Number;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public double getActualNumber() {
            return ActualNumber;
        }

        public void setActualNumber(double ActualNumber) {
            this.ActualNumber = ActualNumber;
        }
    }
}
