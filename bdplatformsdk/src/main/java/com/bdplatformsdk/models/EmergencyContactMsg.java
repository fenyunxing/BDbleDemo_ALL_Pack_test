package com.bdplatformsdk.models;

/**
 * Created by admin on 2018/4/4.
 */

public class EmergencyContactMsg {
    int contactNum = 0;
    String contFrtName = "";
    String contFrtPhone = "";
    String contSecName = "";
    String contSecPhone = "";

    public int getContactNum() {
        return contactNum;
    }

    public void setContactNum(int contactNum) {
        this.contactNum = contactNum;
    }

    public String getContFrtName() {
        return contFrtName;
    }

    public void setContFrtName(String contFrtName) {
        this.contFrtName = contFrtName;
    }

    public String getContFrtPhone() {
        return contFrtPhone;
    }

    public void setContFrtPhone(String contFrtPhone) {
        this.contFrtPhone = contFrtPhone;
    }

    public String getContSecName() {
        return contSecName;
    }

    public void setContSecName(String contSecName) {
        this.contSecName = contSecName;
    }

    public String getContSecPhone() {
        return contSecPhone;
    }

    public void setContSecPhone(String contSecPhone) {
        this.contSecPhone = contSecPhone;
    }
}
