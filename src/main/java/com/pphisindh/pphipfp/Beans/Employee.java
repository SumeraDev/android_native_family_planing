package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/27/2017.
 */

public class Employee {

    public  int EMP_ID;
    public  String EMP_NAME;
    public  String MOBILE;
    public  String CNIC;
    public  int FK_HF_ID;
    public  int PARENT_HF_ID;
    public  int DESIGNATION_ID;
    public  String EMP_CODE;
    public  String emp_desig;


    public Employee(int EMP_ID, String EMP_NAME, String MOBILE, String CNIC, int FK_HF_ID, int PARENT_HF_ID, int DESIGNATION_ID, String EMP_CODE, String emp_desig) {
        this.EMP_ID = EMP_ID;
        this.EMP_NAME = EMP_NAME;
        this.MOBILE = MOBILE;
        this.CNIC = CNIC;
        this.FK_HF_ID = FK_HF_ID;
        this.PARENT_HF_ID = PARENT_HF_ID;
        this.DESIGNATION_ID = DESIGNATION_ID;
        this.EMP_CODE = EMP_CODE;
        this.emp_desig = emp_desig;
    }

    public Employee(int EMP_ID, String EMP_NAME, String MOBILE, String CNIC, int FK_HF_ID, int PARENT_HF_ID, int DESIGNATION_ID, String EMP_CODE) {
        this.EMP_ID = EMP_ID;
        this.EMP_NAME = EMP_NAME;
        this.MOBILE = MOBILE;
        this.CNIC = CNIC;
        this.FK_HF_ID = FK_HF_ID;
        this.PARENT_HF_ID = PARENT_HF_ID;
        this.DESIGNATION_ID = DESIGNATION_ID;
        this.EMP_CODE = EMP_CODE;
    }

    //e.`fk_hfid`,e.`hfid`,e.`empname`,e.`emp_code`,e.`qtr_id`
    public Employee( int FK_HF_ID, int PARENT_HF_ID,String EMP_NAME, String EMP_CODE) {
        this.EMP_NAME = EMP_NAME;
        this.EMP_CODE = EMP_CODE;
        this.FK_HF_ID = FK_HF_ID;
        this.PARENT_HF_ID = PARENT_HF_ID;
    }

    public int getEMP_ID() {
        return EMP_ID;
    }

    public void setEMP_ID(int EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public String getEMP_NAME() {
        return EMP_NAME;
    }

    public void setEMP_NAME(String EMP_NAME) {
        this.EMP_NAME = EMP_NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public int getFK_HF_ID() {
        return FK_HF_ID;
    }

    public void setFK_HF_ID(int FK_HF_ID) {
        this.FK_HF_ID = FK_HF_ID;
    }

    public int getPARENT_HF_ID() {
        return PARENT_HF_ID;
    }

    public void setPARENT_HF_ID(int PARENT_HF_ID) {
        this.PARENT_HF_ID = PARENT_HF_ID;
    }

    public int getDESIGNATION_ID() {
        return DESIGNATION_ID;
    }

    public void setDESIGNATION_ID(int DESIGNATION_ID) {
        this.DESIGNATION_ID = DESIGNATION_ID;
    }

    public String getEMP_CODE() {
        return EMP_CODE;
    }

    public void setEMP_CODE(String EMP_CODE) {
        this.EMP_CODE = EMP_CODE;
    }

    public String getEmp_desig() {
        return emp_desig;
    }

    public void setEmp_desig(String emp_desig) {
        this.emp_desig = emp_desig;
    }

    @Override
    public String toString() {
        return EMP_NAME;
    }
}
