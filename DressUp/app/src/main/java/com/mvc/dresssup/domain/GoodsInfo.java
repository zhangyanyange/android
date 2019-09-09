package com.mvc.dresssup.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy2 on 2017/10/27.
 */


public class GoodsInfo {

    /**
     * Carts : [{"Brand":"A3","Products":[{"Id":"139b0b30-1db3-e711-8118-00155d58e105","Picture":"ProductionImage6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png","Name":"1","SalePrice":"100","MarketPrice":null,"Stock":100,"Num":1,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null},{"Id":"54f6b126-a3b3-e711-8118-00155d58e105","Picture":"ProductionImage/c0/c00b0823c2dc4d68a5f0a80f0117d4c6_nenny7ava43ho.png","Name":"11","SalePrice":"100","MarketPrice":null,"Stock":1,"Num":1,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null},{"Id":"6df4dc4e-a3b3-e711-8118-00155d58e105","Picture":"","Name":"1","SalePrice":"100","MarketPrice":null,"Stock":100,"Num":4,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null}]},{"Brand":"A4","Products":[{"Id":"ae84563f-a3b3-e711-8118-00155d58e105","Picture":"","Name":null,"SalePrice":"100","MarketPrice":null,"Stock":0,"Num":2,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null}]},{"Brand":"D3","Products":[{"Id":"2dc65548-abb3-e711-8118-00155d58e105","Picture":"","Name":"test1","SalePrice":"100","MarketPrice":null,"Stock":1,"Num":4,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null}]}]
     * Total :
     * Actual :
     * Subtract :
     */

    private String Total;
    private String Actual;
    private String Subtract;
    private ArrayList<CartsBean> Carts;

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public String getActual() {
        return Actual;
    }

    public void setActual(String Actual) {
        this.Actual = Actual;
    }

    public String getSubtract() {
        return Subtract;
    }

    public void setSubtract(String Subtract) {
        this.Subtract = Subtract;
    }

    public List<CartsBean> getCarts() {
        return Carts;
    }

    public void setCarts(ArrayList<CartsBean> Carts) {
        this.Carts = Carts;
    }

    public static class CartsBean {
        /**
         * Brand : A3
         * Products : [{"Id":"139b0b30-1db3-e711-8118-00155d58e105","Picture":"ProductionImage6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png","Name":"1","SalePrice":"100","MarketPrice":null,"Stock":100,"Num":1,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null},{"Id":"54f6b126-a3b3-e711-8118-00155d58e105","Picture":"ProductionImage/c0/c00b0823c2dc4d68a5f0a80f0117d4c6_nenny7ava43ho.png","Name":"11","SalePrice":"100","MarketPrice":null,"Stock":1,"Num":1,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null},{"Id":"6df4dc4e-a3b3-e711-8118-00155d58e105","Picture":"","Name":"1","SalePrice":"100","MarketPrice":null,"Stock":100,"Num":4,"Description":null,"Sales":null,"Albums":null,"Discount":"","Category":null}]
         */

        private String Brand;
        private ArrayList<ProductsBean> Products;

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String Brand) {
            this.Brand = Brand;
        }

        public ArrayList<ProductsBean> getProducts() {
            return Products;
        }

        public void setProducts(ArrayList<ProductsBean> Products) {
            this.Products = Products;
        }

        public static class ProductsBean implements Parcelable {
            /**
             * Id : 139b0b30-1db3-e711-8118-00155d58e105
             * Picture : ProductionImage6ed81811afe711811800155d58e105_e5p7vgnziqgbo.png
             * Name : 1
             * SalePrice : 100
             * MarketPrice : null
             * Stock : 100
             * Num : 1
             * Description : null
             * Sales : null
             * Albums : null
             * Discount :
             * Category : null
             */
            private String Brand;
            private String Id;
            private String Picture;
            private String Name;
            private String SalePrice;
            private String MarketPrice;
            private int Stock;
            private int Num;
            private String Description;
            private String Sales;
            private String Albums;
            private String Discount;
            private String Category;

            private boolean choosed;

            public boolean isChoosed() {
                return choosed;
            }

            public void setChoosed(boolean choosed) {
                this.choosed = choosed;
            }

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String brand) {
                Brand = brand;
            }

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

            public String getMarketPrice() {
                return MarketPrice;
            }

            public void setMarketPrice(String MarketPrice) {
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

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getSales() {
                return Sales;
            }

            public void setSales(String Sales) {
                this.Sales = Sales;
            }

            public String getAlbums() {
                return Albums;
            }

            public void setAlbums(String Albums) {
                this.Albums = Albums;
            }

            public String getDiscount() {
                return Discount;
            }

            public void setDiscount(String Discount) {
                this.Discount = Discount;
            }

            public String getCategory() {
                return Category;
            }

            public void setCategory(String Category) {
                this.Category = Category;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.Brand);
                dest.writeString(this.Id);
                dest.writeString(this.Picture);
                dest.writeString(this.Name);
                dest.writeString(this.SalePrice);
                dest.writeString(this.MarketPrice);
                dest.writeInt(this.Stock);
                dest.writeInt(this.Num);
                dest.writeString(this.Description);
                dest.writeString(this.Sales);
                dest.writeString(this.Albums);
                dest.writeString(this.Discount);
                dest.writeString(this.Category);
                dest.writeByte(this.choosed ? (byte) 1 : (byte) 0);
            }

            public ProductsBean() {
            }

            protected ProductsBean(Parcel in) {
                this.Brand = in.readString();
                this.Id = in.readString();
                this.Picture = in.readString();
                this.Name = in.readString();
                this.SalePrice = in.readString();
                this.MarketPrice = in.readString();
                this.Stock = in.readInt();
                this.Num = in.readInt();
                this.Description = in.readString();
                this.Sales = in.readString();
                this.Albums = in.readString();
                this.Discount = in.readString();
                this.Category = in.readString();
                this.choosed = in.readByte() != 0;
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
}
