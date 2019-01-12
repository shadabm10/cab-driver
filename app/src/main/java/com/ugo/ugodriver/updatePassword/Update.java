package com.ugo.ugodriver.updatePassword;

import com.google.gson.annotations.SerializedName;

public class Update {


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
