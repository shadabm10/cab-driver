package com.ugo.ugodriver.authenticate;

import com.google.gson.annotations.SerializedName;

public class Login {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("driver_info")
    private Driver_Info driver_info;



    public Driver_Info getDriver_info() {
        return driver_info;
    }

    public void setDriver_info(Driver_Info driver_info) {
        this.driver_info = driver_info;
    }


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
