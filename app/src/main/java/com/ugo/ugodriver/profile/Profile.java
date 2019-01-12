package com.ugo.ugodriver.profile;

import com.google.gson.annotations.SerializedName;

public class Profile {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("user_info")
    private User_info user_info;


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

    public User_info getUser_info() {
        return user_info;
    }

    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }
}
