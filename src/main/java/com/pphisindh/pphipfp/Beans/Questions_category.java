package com.pphisindh.pphipfp.Beans;

/**
 * Created by LENOVO on 04-Sep-18.
 */

public class Questions_category {
    String id,ques_cat ,is_active ,applyOn;

    public Questions_category(String id, String ques_cat, String is_active, String applyOn) {
        this.id = id;
        this.ques_cat = ques_cat;
        this.is_active = is_active;
        this.applyOn = applyOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQues_cat() {
        return ques_cat;
    }

    public void setQues_cat(String ques_cat) {
        this.ques_cat = ques_cat;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(String applyOn) {
        this.applyOn = applyOn;
    }
}
