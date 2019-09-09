package com.mvc.dresssup.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy2 on 2017/10/30.
 */

public class ProductDetail implements Parcelable {


    /**
     * Id : sample string 1
     * Picture : sample string 2
     * Name : sample string 3
     * SalePrice : sample string 4
     * MarketPrice : sample string 5
     * Stock : 6
     * Num : 7
     * Description : sample string 8
     * Sales : sample string 9
     * Albums : [{"Filename":"sample string 1","OriginalName":"sample string 2"},{"Filename":"sample string 1","OriginalName":"sample string 2"}]
     * Discount : sample string 10
     * Category : sample string 11
     * Brand : sample string 12
     */

    private String Id;
    private String Picture;
    private String Name;
    private String SalePrice;
    private String MarketPrice;
    private int Stock;
    private int Num;
    private String Description;
    private String Sales;
    private String Discount;
    private String Category;
    private String Brand;
    private List<AlbumsBean> Albums;

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

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public List<AlbumsBean> getAlbums() {
        return Albums;
    }

    public void setAlbums(List<AlbumsBean> Albums) {
        this.Albums = Albums;
    }

    public static class AlbumsBean implements Parcelable {
        /**
         * Filename : sample string 1
         * OriginalName : sample string 2
         */

        private String Filename;
        private String OriginalName;

        public String getFilename() {
            return Filename;
        }

        public void setFilename(String Filename) {
            this.Filename = Filename;
        }

        public String getOriginalName() {
            return OriginalName;
        }

        public void setOriginalName(String OriginalName) {
            this.OriginalName = OriginalName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Filename);
            dest.writeString(this.OriginalName);
        }

        public AlbumsBean() {
        }

        protected AlbumsBean(Parcel in) {
            this.Filename = in.readString();
            this.OriginalName = in.readString();
        }

        public static final Creator<AlbumsBean> CREATOR = new Creator<AlbumsBean>() {
            @Override
            public AlbumsBean createFromParcel(Parcel source) {
                return new AlbumsBean(source);
            }

            @Override
            public AlbumsBean[] newArray(int size) {
                return new AlbumsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Picture);
        dest.writeString(this.Name);
        dest.writeString(this.SalePrice);
        dest.writeString(this.MarketPrice);
        dest.writeInt(this.Stock);
        dest.writeInt(this.Num);
        dest.writeString(this.Description);
        dest.writeString(this.Sales);
        dest.writeString(this.Discount);
        dest.writeString(this.Category);
        dest.writeString(this.Brand);
        dest.writeList(this.Albums);
    }

    public ProductDetail() {
    }

    protected ProductDetail(Parcel in) {
        this.Id = in.readString();
        this.Picture = in.readString();
        this.Name = in.readString();
        this.SalePrice = in.readString();
        this.MarketPrice = in.readString();
        this.Stock = in.readInt();
        this.Num = in.readInt();
        this.Description = in.readString();
        this.Sales = in.readString();
        this.Discount = in.readString();
        this.Category = in.readString();
        this.Brand = in.readString();
        this.Albums = new ArrayList<AlbumsBean>();
        in.readList(this.Albums, AlbumsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductDetail> CREATOR = new Parcelable.Creator<ProductDetail>() {
        @Override
        public ProductDetail createFromParcel(Parcel source) {
            return new ProductDetail(source);
        }

        @Override
        public ProductDetail[] newArray(int size) {
            return new ProductDetail[size];
        }
    };
}
