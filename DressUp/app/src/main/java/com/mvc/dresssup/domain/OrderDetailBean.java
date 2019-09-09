package com.mvc.dresssup.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/11/7.
 */


public class OrderDetailBean {

    /**
     * Products : [{"Id":"6","Picture":"Content/images/1.jpg","Name":"斯图（sitoo） 加厚防水PVC墙纸壁纸自粘卧室客厅宿舍寝室背景纸贴壁纸贴画3D彩装膜 9956-4蓝底小花 60厘米3米价","SalePrice":"16.00","MarketPrice":null,"Stock":2,"Num":1,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null,"Brand":null}]
     * Total : null
     * Actual : 100.00
     * Subtract : null
     * OrderNo : C20171106174248
     * CreatDate : 2017/11/6 17:42:48
     * Address : 红
     * Telephone : 18246025978
     * Consignee : 4亿
     * Status : 0
     */

    private Object Total;
    private String Actual;
    private Object Subtract;
    private String OrderNo;
    private String CreatDate;
    private String Address;
    private String Telephone;
    private String Consignee;
    private int Status;
    private List<ProductsBean> Products;

    public Object getTotal() {
        return Total;
    }

    public void setTotal(Object Total) {
        this.Total = Total;
    }

    public String getActual() {
        return Actual;
    }

    public void setActual(String Actual) {
        this.Actual = Actual;
    }

    public Object getSubtract() {
        return Subtract;
    }

    public void setSubtract(Object Subtract) {
        this.Subtract = Subtract;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getCreatDate() {
        return CreatDate;
    }

    public void setCreatDate(String CreatDate) {
        this.CreatDate = CreatDate;
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

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String Consignee) {
        this.Consignee = Consignee;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public List<ProductsBean> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductsBean> Products) {
        this.Products = Products;
    }


    public static class ProductsBean {
        /**
         * Id : 6
         * Picture : Content/images/1.jpg
         * Name : 斯图（sitoo） 加厚防水PVC墙纸壁纸自粘卧室客厅宿舍寝室背景纸贴壁纸贴画3D彩装膜 9956-4蓝底小花 60厘米3米价
         * SalePrice : 16.00
         * MarketPrice : null
         * Stock : 2
         * Num : 1
         * Description : null
         * Sales : null
         * Albums : null
         * Discount :
         * Category : null
         * Brand : null
         */

        private String Id;
        private String Picture;
        private String Name;
        private String SalePrice;
        private Object MarketPrice;
        private int Stock;
        private int Num;
        private Object Description;
        private Object Sales;
        private Object Albums;
        private String Discount;
        private Object Category;
        private Object Brand;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(String SalePrice) {
            this.SalePrice = SalePrice;
        }

        public Object getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(Object MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public Object getDescription() {
            return Description;
        }

        public void setDescription(Object Description) {
            this.Description = Description;
        }

        public Object getSales() {
            return Sales;
        }

        public void setSales(Object Sales) {
            this.Sales = Sales;
        }

        public Object getAlbums() {
            return Albums;
        }

        public void setAlbums(Object Albums) {
            this.Albums = Albums;
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String Discount) {
            this.Discount = Discount;
        }

        public Object getCategory() {
            return Category;
        }

        public void setCategory(Object Category) {
            this.Category = Category;
        }

        public Object getBrand() {
            return Brand;
        }

        public void setBrand(Object Brand) {
            this.Brand = Brand;
        }
    }
}
