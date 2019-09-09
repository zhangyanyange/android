package com.myygjc.ant.project.doman;

/**
 * Created by zy2 on 2017/1/5.
 */




public class MaterialType {


    /**
     * id : 288
     * fullname : 水料
     * name : 水料
     * deleted : false
     * disabled : false
     * number : 01
     */

    private int id;
    private String fullname;
    private String name;
    private boolean deleted;
    private boolean disabled;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
