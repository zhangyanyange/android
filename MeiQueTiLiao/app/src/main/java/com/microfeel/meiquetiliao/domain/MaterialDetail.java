package com.microfeel.meiquetiliao.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/12/5.
 */

public class MaterialDetail {


    /**
     * totalCount : 0
     * listContent : [{"ID":"2ec7947b-432f-40f3-86d5-2e76d7f79d9e","OrderID":"96e8074f-5f91-48f5-ac09-11f65af14bbb","MaterialID":940,"Name":"图纸带保护套","Number":20,"Price":2,"Unit":"个","ActualNumber":0,"Remark":"王岗库---王岗自取","Standard":null,"Brand":null},{"ID":"7937d8b4-6cc8-465e-92e0-49e93814c3ed","OrderID":"96e8074f-5f91-48f5-ac09-11f65af14bbb","MaterialID":934,"Name":"暖气保护罩","Number":30,"Price":1,"Unit":"个","ActualNumber":0,"Remark":"王岗库---王岗自取","Standard":null,"Brand":null}]
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
         * ID : 2ec7947b-432f-40f3-86d5-2e76d7f79d9e
         * OrderID : 96e8074f-5f91-48f5-ac09-11f65af14bbb
         * MaterialID : 940
         * Name : 图纸带保护套
         * Number : 20.0
         * Price : 2.0
         * Unit : 个
         * ActualNumber : 0.0
         * Remark : 王岗库---王岗自取
         * Standard : null
         * Brand : null
         */

        private String ID;
        private String OrderID;
        private int MaterialID;
        private String Name;
        private double Number;
        private double Price;
        private String Unit;
        private double ActualNumber;
        private String Remark;
        private Object Standard;
        private Object Brand;
        private double Count;

        public double getCount() {
            return Count;
        }

        public void setCount(double count) {
            Count = count;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String OrderID) {
            this.OrderID = OrderID;
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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public Object getStandard() {
            return Standard;
        }

        public void setStandard(Object Standard) {
            this.Standard = Standard;
        }

        public Object getBrand() {
            return Brand;
        }

        public void setBrand(Object Brand) {
            this.Brand = Brand;
        }
    }
}
