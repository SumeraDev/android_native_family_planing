package com.pphisindh.pphipfp;

/**
 * Created by Administrator on 8/17/2017.
 */

public class Answers {

    int questionId;
    int optionId;

    String lateDescriptin;
    int isempCode;
    String emp_code;
    int is_active;
    int isPresent;

    public Answers(){}

    public Answers(int questionId, int optionId, String lateDescriptin, int isempCode, String emp_code, int is_active, int isPresent) {
        this.questionId = questionId;
        this.optionId = optionId;
        this.lateDescriptin = lateDescriptin;
        this.isempCode = isempCode;
        this.emp_code = emp_code;
        this.is_active = is_active;
        this.isPresent = isPresent;
    }

    public Answers(int questionId, int optionId) {
        this.questionId = questionId;
        this.optionId = optionId;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public int getOptionId() {
        return optionId;
    }
    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }


    public String getLateDescriptin() {return lateDescriptin;}

    public void setLateDescriptin(String lateDescriptin) {this.lateDescriptin = lateDescriptin;}

    public int getIsempCode() {return isempCode;}

    public void setIsempCode(int isempCode) {this.isempCode = isempCode;}

    public String getEmp_code() {return emp_code;}

    public void setEmp_code(String emp_code) {this.emp_code = emp_code;}

    public int getIs_active() {return is_active;}

    public void setIs_active(int is_active) {this.is_active = is_active;}

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
}
