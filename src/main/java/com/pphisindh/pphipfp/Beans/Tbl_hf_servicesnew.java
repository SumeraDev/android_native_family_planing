package com.pphisindh.pphipfp.Beans;

/**
 * Created by LENOVO on 04-Sep-18.
 */

public class Tbl_hf_servicesnew {
    String id ,servicesName ,is_active;

    public Tbl_hf_servicesnew(String id, String servicesName, String is_active) {
        this.id = id;
        this.servicesName = servicesName;
        this.is_active = is_active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
