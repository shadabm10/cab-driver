package com.ugo.ugodriver.forgotPassword;

import com.google.gson.annotations.SerializedName;

public class Forgot {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("OTP")
    private String otp;

    @SerializedName("id")
    private String id;





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
