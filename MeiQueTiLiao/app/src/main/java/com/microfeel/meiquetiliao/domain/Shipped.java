package com.microfeel.meiquetiliao.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/12/7.
 */

public class Shipped {

    /**
     * totalCount : 0
     * listContent : [{"Address":"金泰湖滨绿茵G8-3-202","Code":"MO201711050015","Contact":"王建宝","Date":"9999-12-31T23:59:59.997","ID":"e2afdc78-06b9-4b3a-a143-6a274eb2d18e","State":"订单完成"}]
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
         * Address : 金泰湖滨绿茵G8-3-202
         * Code : MO201711050015
         * Contact : 王建宝
         * Date : 9999-12-31T23:59:59.997
         * ID : e2afdc78-06b9-4b3a-a143-6a274eb2d18e
         * State : 订单完成
         */

        private String Address;
        private String Code;
        private String Contact;
        private String Date;
        private String ID;
        private String State;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }
    }
}
