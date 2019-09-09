package com.microfeel.meiquetiliao.domain;

/**
 * Created by zy2 on 2018/1/15.
 */

public class LimitMoney {


    /**
     * stringContent : 5000
     * result : 0
     * message : 调用成功
     */

    private String stringContent;
    private int result;
    private String message;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
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
}
