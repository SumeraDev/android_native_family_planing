package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/16/2017.
 */

public class Questionsbean {
    int id;
    String question;
    int is_hr;
    int mo_fmo_both;
    int ques_catId;
    String ques_cat;



    public Questionsbean(int id, String question, int is_hr, int mo_fmo_both, int ques_catId, String ques_cat) {
        this.id = id;
        this.question = question;
        this.is_hr = is_hr;
        this.mo_fmo_both = mo_fmo_both;
        this.ques_catId = ques_catId;
        this.ques_cat = ques_cat;
    }

    public Questionsbean(int id, String question, int is_hr) {
        this.id = id;
        this.question = question;
        this.is_hr = is_hr;
    }

    public Questionsbean(int id, String question, int is_hr, int mo_fmo_both,int ques_catId) {
        this.id = id;
        this.question = question;
        this.is_hr = is_hr;
        this.mo_fmo_both = mo_fmo_both;
        this.ques_catId=ques_catId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getIs_hr() {
        return is_hr;
    }

    public void setIs_hr(int is_hr) {
        this.is_hr = is_hr;
    }

    public int getMo_fmo_both() {return mo_fmo_both;}

    public void setMo_fmo_both(int mo_fmo_both) {this.mo_fmo_both = mo_fmo_both;}

    public int getQues_catId() {
        return ques_catId;
    }

    public void setQues_catId(int ques_catId) {
        this.ques_catId = ques_catId;
    }

    public String getQues_cat() {
        return ques_cat;
    }

    public void setQues_cat(String ques_cat) {
        this.ques_cat = ques_cat;
    }
}
