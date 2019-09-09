package com.mvc.dresssup.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/10/25.
 */


public class AddressStateBean {

    /**
     * totalCount : 0
     * listContent : [{"Id":"2bcb647c-47b9-e711-8118-00155d58e105","Address":"啊","Telephone":"啊","IsDefault":0,"Consignee":"啊"},{"Id":"b7c7fa9f-47b9-e711-8118-00155d58e105","Address":"啊","Telephone":"啊","IsDefault":0,"Consignee":"了"}]
     * result : 1
     * message : 成功
     */

    private int totalCount;
    private int result;
    private String message;
    private List<AddressBean> listContent;

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

    public List<AddressBean> getListContent() {
        return listContent;
    }

    public void setListContent(List<AddressBean> listContent) {
        this.listContent = listContent;
    }

}
