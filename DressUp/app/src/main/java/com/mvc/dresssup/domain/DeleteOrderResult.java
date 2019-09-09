package com.mvc.dresssup.domain;

/**
 * Created by zy2 on 2017/11/6.
 */

public class DeleteOrderResult {

    /**
     * result : 1
     * message : 删除成功
     */

    private int result;
    private String message;

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
