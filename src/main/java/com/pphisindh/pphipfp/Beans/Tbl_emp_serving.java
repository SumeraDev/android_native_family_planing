package com.pphisindh.pphipfp.Beans;

/**
 * Created by LENOVO on 04-Sep-18.
 */

public class Tbl_emp_serving {
    String id ,ques_catId ,fk_hfid ,hfid ,qtr_id ,visit_masterId ,emp_code,empname ,is_active;

    public Tbl_emp_serving(String id, String ques_catId, String fk_hfid, String hfid, String qtr_id, String visit_masterId, String emp_code,String empname, String is_active) {
        this.id = id;
        this.ques_catId = ques_catId;
        this.fk_hfid = fk_hfid;
        this.hfid = hfid;
        this.qtr_id = qtr_id;
        this.visit_masterId = visit_masterId;
        this.emp_code = emp_code;
        this.empname=empname;
        this.is_active = is_active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQues_catId() {
        return ques_catId;
    }

    public void setQues_catId(String ques_catId) {
        this.ques_catId = ques_catId;
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

    public String getVisit_masterId() {
        return visit_masterId;
    }

    public void setVisit_masterId(String visit_masterId) {
        this.visit_masterId = visit_masterId;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
