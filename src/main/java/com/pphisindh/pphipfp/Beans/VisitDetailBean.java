package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/21/2017.
 */

public class VisitDetailBean {
    int vm_id;
    String vm_code;
    int qid;
    int oid;
    int is_active;

    public VisitDetailBean(int vm_id, String vm_code, int qid, int oid, int is_active) {
        this.vm_id = vm_id;
        this.vm_code = vm_code;
        this.qid = qid;
        this.oid = oid;
        this.is_active = is_active;
    }

    public int getVm_id() {
        return vm_id;
    }

    public void setVm_id(int vm_id) {
        this.vm_id = vm_id;
    }

    public String getVm_code() {
        return vm_code;
    }

    public void setVm_code(String vm_code) {
        this.vm_code = vm_code;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}
