package com.mvc.dresssup.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/11/3.
 */

public class OrderAllBean {

    /**
     * Actual : 100.00
     * CreatDate : 2017/11/1 17:06:10
     * Id : 4
     * Num : 2
     * OrderNo : C20171101170610
     * Products : [{"Id":"4","Name":"斯图（sitoo） 加厚防水PVC墙纸壁纸自粘卧室客厅宿舍寝室背景纸贴壁纸贴画3D彩装膜 9956-4蓝底小花 60厘米3米价","Num":0,"Picture":"Content/images/1.jpg","Stock":0},{"Id":"4","Name":"旗航壁纸 欧式无纺布墙纸简约现代电视背景墙壁纸客厅卧室墙纸 菱格 00337浅咖色","Num":0,"Picture":"Content/images/1.jpg","Stock":0}]
     * Status : 0
     */

    private String Actual;
    private String CreatDate;
    private int Id;
    private int Num;
    private String OrderNo;
    private int Status;
    private List<ProductsBean> Products;

    public String getActual() {
        return Actual;
    }

    public void setActual(String Actual) {
        this.Actual = Actual;
    }

    public String getCreatDate() {
        return CreatDate;
    }

    public void setCreatDate(String CreatDate) {
        this.CreatDate = CreatDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
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
         * Id : 4
         * Name : 斯图（sitoo） 加厚防水PVC墙纸壁纸自粘卧室客厅宿舍寝室背景纸贴壁纸贴画3D彩装膜 9956-4蓝底小花 60厘米3米价
         * Num : 0
         * Picture : Content/images/1.jpg
         * Stock : 0
         */

        private String Id;
        private String Name;
        private int Num;
        private String Picture;
        private int Stock;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }
    }
}
