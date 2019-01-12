package com.ugo.ugodriver.notarization;

import com.google.gson.annotations.SerializedName;

public class Register {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("OTP")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
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
