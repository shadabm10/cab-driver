package com.ugo.ugodriver.fragments.myEarning;

import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 2/3/18.
 */

public class EarningResponce {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("earning_info")
    private MyEarningData earning_info;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyEarningData getEarning_info() {
        return earning_info;
    }

    public void setEarning_info(MyEarningData earning_info) {
        this.earning_info = earning_info;
    }
}
