package com.mvc.microfeel.domain;

/**
 * Created by zy2 on 2017/8/29.
 */


public class CaptchaDataBean {

    /**
     * EmployeeName : null
     * EnterpriseName : null
     * orgName : null
     * Result : 0
     * Message : 调用成功
     */

    private String EmployeeName;
    private String EnterpriseName;
    private String orgName;
    private int Result;
    private String Message;


    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public String getEnterpriseName() {
        return EnterpriseName;
    }

    public void setEnterpriseName(String EnterpriseName) {
        this.EnterpriseName = EnterpriseName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
