package com.mvc.dresssup.domain;

/**
 * Created by zy2 on 2017/9/25.
 */

public class Classisification {
    private boolean isClick;
    private String pinpai;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getPinpai() {
        return pinpai;
    }

    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }
}
