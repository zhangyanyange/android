package com.microfeel.meiquetiliao.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zy2 on 2017/12/15.
 */

public class RetreactMaterial implements Parcelable {

    /**
     * totalCount : 0
     * listContent : [{"Count":30,"ID":"3824","Name":"B瑞好20 45°弯头","Price":1.45,"Unit":"个"},{"Count":50,"ID":"3987","Name":"20不锈钢装饰盖","Price":0.8,"Unit":"个"},{"Count":5,"ID":"6299","Name":"6分/25吊卡(绿）","Price":8,"Unit":"袋"},{"Count":100,"ID":"3989","Name":"代垫丝堵","Price":0.5,"Unit":"个"},{"Count":5,"ID":"3888","Name":"沃尔德2.5平方线100m（白）","Price":141,"Unit":"捆"},{"Count":240,"ID":"3896","Name":"蚂蚁阳光精品86底盒","Price":1.3,"Unit":"个"},{"Count":1,"ID":"3312","Name":"飞利浦超5类网线","Price":520,"Unit":"捆"},{"Count":5,"ID":"3913","Name":"光纤入户箱（塑料）300*400*120","Price":95,"Unit":"个"},{"Count":10,"ID":"3933","Name":"泰山L型边角2700*0.48","Price":5.5,"Unit":"根"},{"Count":2,"ID":"4977","Name":"定向结构OSB板14mm（大）","Price":124,"Unit":"张"},{"Count":20,"ID":"3925","Name":"龙牌中国红净荃防霉石膏板","Price":25.5,"Unit":"张"},{"Count":20,"ID":"3930","Name":"泰山50副骨2700*50*0.58（国标）","Price":6.5,"Unit":"根"},{"Count":6,"ID":"3931","Name":"泰山32卡式主骨2700*0.70","Price":7,"Unit":"根"},{"Count":20,"ID":"3932","Name":"泰山U型边骨2700*0.51（国标）","Price":6,"Unit":"根"},{"Count":15,"ID":"3956","Name":"美巢821腻子粉20kg","Price":18.5,"Unit":"袋"},{"Count":20,"ID":"940","Name":"图纸带保护套","Price":2,"Unit":"个"},{"Count":30,"ID":"934","Name":"暖气保护罩","Price":1,"Unit":"个"},{"Count":20,"ID":"3537","Name":"20像塑保温管（红）","Price":3,"Unit":"根"},{"Count":10,"ID":"3309","Name":"瑞好20双活接球阀","Price":48.5,"Unit":"个"},{"Count":15,"ID":"3308","Name":"瑞好20内丝三通","Price":9.35,"Unit":"个"},{"Count":300,"ID":"3302","Name":"瑞好20弯头90°","Price":1.49,"Unit":"个"},{"Count":80,"ID":"3307","Name":"瑞好20内丝弯头","Price":9.63,"Unit":"个"},{"Count":10,"ID":"3310","Name":"瑞好20双联内丝弯头","Price":23.1,"Unit":"个"},{"Count":30,"ID":"3538","Name":"20像塑保温管（蓝）","Price":3,"Unit":"根"},{"Count":2,"ID":"3311","Name":"飞利浦电视线（绿）","Price":180,"Unit":"捆"}]
     * result : 0
     * message : 调用成功
     */

    private int totalCount;
    private int result;
    private String message;
    private ArrayList<ListContentBean> listContent;

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

    public ArrayList<ListContentBean> getListContent() {
        return listContent;
    }

    public void setListContent(ArrayList<ListContentBean> listContent) {
        this.listContent = listContent;
    }
    public static class ListContentBean implements Parcelable {
        /**
         * Count : 30.0
         * ID : 3824
         * Name : B瑞好20 45°弯头
         * Price : 1.45
         * Unit : 个
         */

        private double Count;
        private String ID;
        private String Name;
        private double Price;
        private String Unit;
        private int Number;

        public int getNumber() {
            return Number;
        }

        public void setNumber(int number) {
            Number = number;
        }

        public double getCount() {
            return Count;
        }

        public void setCount(double Count) {
            this.Count = Count;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
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

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.Count);
            dest.writeString(this.ID);
            dest.writeString(this.Name);
            dest.writeDouble(this.Price);
            dest.writeString(this.Unit);
            dest.writeInt(this.Number);
        }

        public ListContentBean() {
        }

        protected ListContentBean(Parcel in) {
            this.Count = in.readDouble();
            this.ID = in.readString();
            this.Name = in.readString();
            this.Price = in.readDouble();
            this.Unit = in.readString();
            this.Number = in.readInt();
        }

        public static final Parcelable.Creator<ListContentBean> CREATOR = new Parcelable.Creator<ListContentBean>() {
            @Override
            public ListContentBean createFromParcel(Parcel source) {
                return new ListContentBean(source);
            }

            @Override
            public ListContentBean[] newArray(int size) {
                return new ListContentBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalCount);
        dest.writeInt(this.result);
        dest.writeString(this.message);
        dest.writeTypedList(this.listContent);
    }

    public RetreactMaterial() {
    }

    protected RetreactMaterial(Parcel in) {
        this.totalCount = in.readInt();
        this.result = in.readInt();
        this.message = in.readString();
        this.listContent = in.createTypedArrayList(ListContentBean.CREATOR);
    }

    public static final Parcelable.Creator<RetreactMaterial> CREATOR = new Parcelable.Creator<RetreactMaterial>() {
        @Override
        public RetreactMaterial createFromParcel(Parcel source) {
            return new RetreactMaterial(source);
        }

        @Override
        public RetreactMaterial[] newArray(int size) {
            return new RetreactMaterial[size];
        }
    };
}
