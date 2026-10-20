package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/23/2017.
 */

public class AttendanceBean {

    String vm_code;
    int qId;
    int oId;
    String lateDescriptin;
    int isempCode;
    String emp_code;
    int is_active;
    int isPresent;

    public AttendanceBean(String vm_code, int qId, int oId, String lateDescriptin, int isempCode, String emp_code, int is_active,int isPresent) {

        this.vm_code = vm_code;
        this.qId = qId;
        this.oId = oId;
        this.lateDescriptin = lateDescriptin;
        this.isempCode = isempCode;
        this.emp_code = emp_code;
        this.is_active = is_active;
        this.isPresent=isPresent;
    }


    public String getVm_code() {
        return vm_code;
    }

    public void setVm_code(String vm_code) {
        this.vm_code = vm_code;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getLateDescriptin() {
        return lateDescriptin;
    }

    public void setLateDescriptin(String lateDescriptin) {
        this.lateDescriptin = lateDescriptin;
    }

    public int getIsempCode() {
        return isempCode;
    }

    public void setIsempCode(int isempCode) {
        this.isempCode = isempCode;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
}
