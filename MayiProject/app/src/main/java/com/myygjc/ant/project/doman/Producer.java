package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/3/16.
 */


public class Producer {

    /**
     * deleted : false
     * disabled : false
     * fullname : 电料_金上桥
     * id : 4060
     * name : 金上桥
     * number : 02.001
     */

    private boolean deleted;
    private boolean disabled;
    private String fullname;
    private int id;
    private String name;
    private String number;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
