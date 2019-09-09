package com.microfeel.meiquetiliao.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/12/5.
 */

public class Material {


    /**
     * totalCount : 0
     * listContent : [{"ID":932,"Name":"基础材料","Price":0,"Qty":0,"Unit":"","IsDetail":false}]
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
         * ID : 932
         * Name : 基础材料
         * Price : 0.0
         * Qty : 0.0
         * Unit :
         * IsDetail : false
         */

        private int ID;
        private String Name;
        private double Price;
        private double Qty;
        private String Unit;
        private boolean IsDetail;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public double getQty() {
            return Qty;
        }

        public void setQty(double Qty) {
            this.Qty = Qty;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public boolean isIsDetail() {
            return IsDetail;
        }

        public void setIsDetail(boolean IsDetail) {
            this.IsDetail = IsDetail;
        }
    }
}
