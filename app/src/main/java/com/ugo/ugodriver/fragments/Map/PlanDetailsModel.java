package com.ugo.ugodriver.fragments.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 6/3/18.
 */

public class PlanDetailsModel {


    @SerializedName("plan_name")
    private String plan_name;


    @SerializedName("plan_amount")
    private String plan_amount;


    @SerializedName("plan_trip")
    private String plan_trip;


    @SerializedName("plan_description")
    private String plan_description;


    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_amount() {
        return plan_amount;
    }

    public void setPlan_amount(String plan_amount) {
        this.plan_amount = plan_amount;
    }

    public String getPlan_trip() {
        return plan_trip;
    }

    public void setPlan_trip(String plan_trip) {
        this.plan_trip = plan_trip;
    }

    public String getPlan_description() {
        return plan_description;
    }

    public void setPlan_description(String plan_description) {
        this.plan_description = plan_description;
    }
}
