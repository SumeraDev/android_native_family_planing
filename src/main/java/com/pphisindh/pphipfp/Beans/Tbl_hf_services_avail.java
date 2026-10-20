package com.pphisindh.pphipfp.Beans;

/**
 * Created by LENOVO on 04-Sep-18.
 */

public class Tbl_hf_services_avail {
    String id ,serviceType_id ,fk_hfid ,hfid ,qtr_id ,is_active;

    public Tbl_hf_services_avail(String id, String serviceType_id, String fk_hfid, String hfid, String qtr_id, String is_active) {
        this.id = id;
        this.serviceType_id = serviceType_id;
        this.fk_hfid = fk_hfid;
        this.hfid = hfid;
        this.qtr_id = qtr_id;
        this.is_active = is_active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType_id() {
        return serviceType_id;
    }

    public void setServiceType_id(String serviceType_id) {
        this.serviceType_id = serviceType_id;
    }

    public String getFk_hfid() {
        return fk_hfid;
    }

    public void setFk_hfid(String fk_hfid) {
        this.fk_hfid = fk_hfid;
    }

    public String getHfid() {
        return hfid;
    }

    public void setHfid(String hfid) {
        this.hfid = hfid;
    }

    public String getQtr_id() {
        return qtr_id;
    }

    public void setQtr_id(String qtr_id) {
        this.qtr_id = qtr_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
