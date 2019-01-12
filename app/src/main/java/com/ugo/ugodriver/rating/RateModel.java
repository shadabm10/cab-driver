package com.ugo.ugodriver.rating;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 2/14/2018.
 */

public class RateModel {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

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


}
