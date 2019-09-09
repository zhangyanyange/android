package com.mvc.dresssup.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy2 on 2017/10/26.
 */

public class ClassificationProduct {

    /**
     * Brand : {"Id":"5be2627b-10af-e711-8118-00155d58e105","Name":"A3"}
     * Categorys : [{"Id":"","Name":"全部"},{"Id":"53d36dbf-10af-e711-8118-00155d58e105","Name":"A11","ParentId":""},{"Id":"3a4ccfca-10af-e711-8118-00155d58e105","Name":"A22","ParentId":""}]
     * Products : [{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"fd6ed818-11af-e711-8118-00155d58e105","MarketPrice":"","Name":"1","Num":0,"Picture":"ProductionImage/fd/fd6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png","SalePrice":"","Stock":100},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"96fb7312-10b3-e711-8118-00155d58e105","MarketPrice":"","Name":"1","Num":0,"Picture":"","SalePrice":"","Stock":100},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"196e7caa-14b3-e711-8118-00155d58e105","MarketPrice":"","Name":"1","Num":0,"Picture":"","SalePrice":"","Stock":100},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"beb3d8fa-17b3-e711-8118-00155d58e105","MarketPrice":"","Name":"1","Num":0,"Picture":"ProductionImage/fd/fd6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png","SalePrice":"","Stock":100},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"df384e6b-19b3-e711-8118-00155d58e105","MarketPrice":"","Name":"1","Num":0,"Picture":"ProductionImage/fd/fd6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png","SalePrice":"","Stock":100},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"c00b0823-c2dc-4d68-a5f0-a80f0117d4c6","MarketPrice":"","Name":"11","Num":0,"Picture":"ProductionImage/c0/c00b0823c2dc4d68a5f0a80f0117d4c6_nenny7ava43ho.png","SalePrice":"","Stock":1},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"acb4eb1c-506f-46c0-9412-a81000f51899","MarketPrice":"","Num":0,"Picture":"","SalePrice":"","Stock":1},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"f7281e22-beae-491c-b08a-a81000f6feb0","MarketPrice":"","Num":0,"Picture":"","SalePrice":"","Stock":0},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"1e11bc9c-53b5-4e83-ac8f-a81001171b0b","MarketPrice":"","Num":0,"Picture":"","SalePrice":"","Stock":1},{"Category":"53d36dbf-10af-e711-8118-00155d58e105","Id":"40339c6b-55bf-4e42-a223-a810011820e7","MarketPrice":"","Name":"r","Num":0,"Picture":"ProductionImage/40/40339c6b55bf4e42a223a810011820e7_azkxtz3tjk3xa.png","SalePrice":"","Stock":0},{"Category":"3a4ccfca-10af-e711-8118-00155d58e105","Id":"2d8ee3cb-98cc-4e2e-9f53-a8100118b545","MarketPrice":"","Name":"6","Num":0,"Picture":"","SalePrice":"","Stock":6},{"Category":"3a4ccfca-10af-e711-8118-00155d58e105","Id":"2d1a2d95-da05-4e25-afd7-a81100f16e1c","MarketPrice":"","Name":"3","Num":0,"Picture":"","SalePrice":"","Stock":1},{"Category":"3a4ccfca-10af-e711-8118-00155d58e105","Id":"3f9273ef-21c0-458f-9cf4-a81100fea1f1","MarketPrice":"","Num":0,"Picture":"","SalePrice":"","Stock":1},{"Category":"3a4ccfca-10af-e711-8118-00155d58e105","Id":"ce1524c4-07a9-4403-8851-a812009454bd","MarketPrice":"","Name":"test","Num":0,"Picture":"","SalePrice":"","Stock":1}]
     */

    private BrandBean Brand;
    private List<CategorysBean> Categorys;
    private ArrayList<ProductsBean> Products;

    public BrandBean getBrand() {
        return Brand;
    }

    public void setBrand(BrandBean Brand) {
        this.Brand = Brand;
    }

    public List<CategorysBean> getCategorys() {
        return Categorys;
    }

    public void setCategorys(List<CategorysBean> Categorys) {
        this.Categorys = Categorys;
    }

    public ArrayList<ProductsBean> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<ProductsBean> Products) {
        this.Products = Products;
    }

    public static class BrandBean {
        /**
         * Id : 5be2627b-10af-e711-8118-00155d58e105
         * Name : A3
         */

        private String Id;
        private String Name;

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
    }

    public static class CategorysBean {
        /**
         * Id :
         * Name : 全部
         * ParentId :
         */

        private String Id;
        private String Name;
        private String ParentId;

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

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }
    }

    public static class ProductsBean implements Parcelable {
        /**
         * Category : 3a4ccfca-10af-e711-8118-00155d58e105
         * Id : fd6ed818-11af-e711-8118-00155d58e105
         * MarketPrice :
         * Name : 1
         * Num : 0
         * Picture : ProductionImage/fd/fd6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png
         * SalePrice :
         * Stock : 100
         */

        private String Category;
        private String Id;
        private String MarketPrice;
        private String Name;
        private int Num;
        private String Picture;
        private String SalePrice;
        private int Stock;

        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(String MarketPrice) {
            this.MarketPrice = MarketPrice;
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

        public String getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(String SalePrice) {
            this.SalePrice = SalePrice;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Category);
            dest.writeString(this.Id);
            dest.writeString(this.MarketPrice);
            dest.writeString(this.Name);
            dest.writeInt(this.Num);
            dest.writeString(this.Picture);
            dest.writeString(this.SalePrice);
            dest.writeInt(this.Stock);
        }

        public ProductsBean() {
        }

        protected ProductsBean(Parcel in) {
            this.Category = in.readString();
            this.Id = in.readString();
            this.MarketPrice = in.readString();
            this.Name = in.readString();
            this.Num = in.readInt();
            this.Picture = in.readString();
            this.SalePrice = in.readString();
            this.Stock = in.readInt();
        }

        public static final Parcelable.Creator<ProductsBean> CREATOR = new Parcelable.Creator<ProductsBean>() {
            @Override
            public ProductsBean createFromParcel(Parcel source) {
                return new ProductsBean(source);
            }

            @Override
            public ProductsBean[] newArray(int size) {
                return new ProductsBean[size];
            }
        };
    }
}
