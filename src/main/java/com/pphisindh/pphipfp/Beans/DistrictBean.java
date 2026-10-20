package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/16/2017.
 */

public class DistrictBean {

    public  int hid;
    public  int HF_ID;
    public  String HF_DISP_NAME;
    public  String HF_CONTACT_NUM;
    public  int HF_OFFICE_LEVEL;
    public  int HF_PARENT_OFFICE;
    public  int distType;

    public DistrictBean(int hid, int HF_ID, String HF_DISP_NAME, String HF_CONTACT_NUM, int HF_OFFICE_LEVEL, int HF_PARENT_OFFICE, int distType) {
        this.hid = hid;
        this.HF_ID = HF_ID;
        this.HF_DISP_NAME = HF_DISP_NAME;
        this.HF_CONTACT_NUM = HF_CONTACT_NUM;
        this.HF_OFFICE_LEVEL = HF_OFFICE_LEVEL;
        this.HF_PARENT_OFFICE = HF_PARENT_OFFICE;
        this.distType = distType;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getHF_ID() {
        return HF_ID;
    }

    public void setHF_ID(int HF_ID) {
        this.HF_ID = HF_ID;
    }

    public String getHF_DISP_NAME() {
        return HF_DISP_NAME;
    }

    public void setHF_DISP_NAME(String HF_DISP_NAME) {
        this.HF_DISP_NAME = HF_DISP_NAME;
    }

    public String getHF_CONTACT_NUM() {
        return HF_CONTACT_NUM;
    }

    public void setHF_CONTACT_NUM(String HF_CONTACT_NUM) {
        this.HF_CONTACT_NUM = HF_CONTACT_NUM;
    }

    public int getHF_OFFICE_LEVEL() {
        return HF_OFFICE_LEVEL;
    }

    public void setHF_OFFICE_LEVEL(int HF_OFFICE_LEVEL) {
        this.HF_OFFICE_LEVEL = HF_OFFICE_LEVEL;
    }

    public int getHF_PARENT_OFFICE() {
        return HF_PARENT_OFFICE;
    }

    public void setHF_PARENT_OFFICE(int HF_PARENT_OFFICE) {
        this.HF_PARENT_OFFICE = HF_PARENT_OFFICE;
    }

    public int getDistType() {
        return distType;
    }

    public void setDistType(int distType) {
        this.distType = distType;
    }

    @Override
    public String toString() {
        return  HF_DISP_NAME;
    }
}
