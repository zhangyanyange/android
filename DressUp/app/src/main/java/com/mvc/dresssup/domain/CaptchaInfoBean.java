package com.mvc.dresssup.domain;

/**
 * Created by zy2 on 2017/1/19.
 */

public class CaptchaInfoBean {

    /**
     * result : 0
     * message : 验证码已发送,10分钟内有效.
     */

    private int result;
    private String message;
    private String stringContent;

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
