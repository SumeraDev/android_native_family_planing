package com.pphisindh.pphipfp.Beans;

/**
 * Created by Administrator on 8/17/2017.
 */

public class OptionBean {

    int optionId;
    String option;

    public OptionBean(int optionId, String option) {
        this.optionId = optionId;
        this.option = option;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
