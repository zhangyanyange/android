package com.microfeel.meiquetiliao.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/12/4.
 */

public class MentionMaterial {

    /**
     * totalCount : 0
     * listContent : [{"ID":"9210fa7b-18cc-42b8-b977-58b5cb0ab26c","ProjectID":"6daf3a67-b5a9-4af1-b583-67586c640ee4","Code":"MO201711270014","CreaterID":"01aebeb4-de8c-4f84-a474-1c5f644abb90","LiftNum":0,"FloorNum":0,"ShipDistance":0,"ShippingCost":7,"HShipCost":8,"VShipCost":9,"IsSelfPick":true,"CreateTime":"2017-11-27T11:47:42.527","SubmitTime":"2017-11-27T11:48:16.993","CallBackTime":null,"PostTime":null,"Remark":"王岗库---王岗自取","RequireTime":"2017-11-27T00:00:00","Receiver":"王建宝","Contact":"13633620246","Address":"恒祥空间7-3-1703"},{"ID":"0bb2dae0-0fc4-4ae9-a833-a5be5fe5a873","ProjectID":"6daf3a67-b5a9-4af1-b583-67586c640ee4","Code":"MO201712010027","CreaterID":"01aebeb4-de8c-4f84-a474-1c5f644abb90","LiftNum":0,"FloorNum":0,"ShipDistance":0,"ShippingCost":7,"HShipCost":8,"VShipCost":9,"IsSelfPick":true,"CreateTime":"2017-12-01T11:10:08.06","SubmitTime":"2017-12-01T11:12:08.26","CallBackTime":null,"PostTime":null,"Remark":"王岗库---王岗自取","RequireTime":"2017-12-02T00:00:00","Receiver":"王建宝","Contact":"13633620246","Address":"恒祥空间7-3-1703"}]
     * result : 0
     * message : 调用成功
     */

    private int totalCount;
    private int result;
    private String message;
    private List<ListContentBean> listContent;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListContentBean> getListContent() {
        return listContent;
    }

    public void setListContent(List<ListContentBean> listContent) {
        this.listContent = listContent;
    }

    public static class ListContentBean {
        /**
         * ID : 9210fa7b-18cc-42b8-b977-58b5cb0ab26c
         * ProjectID : 6daf3a67-b5a9-4af1-b583-67586c640ee4
         * Code : MO201711270014
         * CreaterID : 01aebeb4-de8c-4f84-a474-1c5f644abb90
         * LiftNum : 0
         * FloorNum : 0
         * ShipDistance : 0.0
         * ShippingCost : 7.0
         * HShipCost : 8.0
         * VShipCost : 9.0
         * IsSelfPick : true
         * CreateTime : 2017-11-27T11:47:42.527
         * SubmitTime : 2017-11-27T11:48:16.993
         * CallBackTime : null
         * PostTime : null
         * Remark : 王岗库---王岗自取
         * RequireTime : 2017-11-27T00:00:00
         * Receiver : 王建宝
         * Contact : 13633620246
         * Address : 恒祥空间7-3-1703
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
        private Object CallBackTime;
        private Object PostTime;
        private String Remark;
        private String RequireTime;
        private String Receiver;
        private String Contact;
        private String Address;

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

        public Object getCallBackTime() {
            return CallBackTime;
        }

        public void setCallBackTime(Object CallBackTime) {
            this.CallBackTime = CallBackTime;
        }

        public Object getPostTime() {
            return PostTime;
        }

        public void setPostTime(Object PostTime) {
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
    }
}
