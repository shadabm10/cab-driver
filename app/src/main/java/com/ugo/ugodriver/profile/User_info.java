package com.ugo.ugodriver.profile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/23/18.
 */

public class User_info {

    @SerializedName("id")
    private String uid;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("approval_status")
    private String approval_status;

    @SerializedName("driver_image")
    private String driver_image;

    @SerializedName("driver_doc")
    private String driver_doc;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getDriver_image() {
        return driver_image;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }

    public String getDriver_doc() {
        return driver_doc;
    }

    public void setDriver_doc(String driver_doc) {
        this.driver_doc = driver_doc;
    }
}
