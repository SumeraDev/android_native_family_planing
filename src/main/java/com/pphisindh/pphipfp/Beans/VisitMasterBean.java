package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/21/2017.
 */

public class VisitMasterBean {
    String code;
    int hfid;
    int phfid;
    int emp_id;
    String start_time;
    String end_time;
    int is_active;
    String longitude;
    String latitude;
    String imgsource;
    String mo_empcode;
    String fmo_empcode;
    String qtr_id;

    public VisitMasterBean(String code, int hfid, int phfid, int emp_id, String start_time, String end_time, int is_active, String longitude, String latitude, String imgsource, String mo_empcode, String fmo_empcode,String qtr_id) {
        this.code = code;
        this.hfid = hfid;
        this.phfid = phfid;
        this.emp_id = emp_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.is_active = is_active;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imgsource = imgsource;
        this.mo_empcode = mo_empcode;
        this.fmo_empcode = fmo_empcode;
        this.qtr_id=qtr_id;
    }

    /*public VisitMasterBean(String code, int hfid, int phfid, int emp_id, String start_time, String end_time, int is_active, String longitude, String latitude, String imgsource) {
        this.code = code;
        this.hfid = hfid;
        this.phfid = phfid;
        this.emp_id = emp_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.is_active = is_active;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imgsource = imgsource;
    }*/

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHfid() {
        return hfid;
    }

    public void setHfid(int hfid) {
        this.hfid = hfid;
    }

    public int getPhfid() {
        return phfid;
    }

    public void setPhfid(int phfid) {
        this.phfid = phfid;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getImgsource() {
        return imgsource;
    }

    public void setImgsource(String imgsource) {
        this.imgsource = imgsource;
    }

    public String getMo_empcode() { return mo_empcode;}

    public void setMo_empcode(String mo_empcode) {this.mo_empcode = mo_empcode;}

    public String getFmo_empcode() {return fmo_empcode;}

    public void setFmo_empcode(String fmo_empcode) {this.fmo_empcode = fmo_empcode;}

    public String getQtr_id() {
        return qtr_id;
    }

    public void setQtr_id(String qtr_id) {
        this.qtr_id = qtr_id;
    }
}
