package com.ugo.ugodriver.fragments.Map;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by developer on 15/3/18.
 */

public class FareDetailsModel implements Serializable {

    @SerializedName("total_distance_travelled")
    private String total_distance_travelled;

    @SerializedName("total_time_taken")
    private String total_time_taken;

    @SerializedName("total_fare")
    private String total_fare;

    public String getGst_fair() {
        return gst_fair;
    }

    public void setGst_fair(String gst_fair) {
        this.gst_fair = gst_fair;
    }

    @SerializedName("gst_fair")
    private String gst_fair;

    public String getCommission_fair() {
        return commission_fair;
    }

    public void setCommission_fair(String commission_fair) {
        this.commission_fair = commission_fair;
    }

    @SerializedName("commission_fair")
    private String commission_fair;

    public String getWaiting_fair() {
        return waiting_fair;
    }

    public void setWaiting_fair(String waiting_fair) {
        this.waiting_fair = waiting_fair;
    }

    @SerializedName("waiting_fair")
    private String waiting_fair;

    public String getSub_total_fare() {
        return sub_total_fare;
    }

    public void setSub_total_fare(String sub_total_fare) {
        this.sub_total_fare = sub_total_fare;
    }

    @SerializedName("sub_total_fare")
    private String sub_total_fare;





    public String getTotal_distance_travelled() {
        return total_distance_travelled;
    }

    public void setTotal_distance_travelled(String total_distance_travelled) {
        this.total_distance_travelled = total_distance_travelled;
    }

    public String getTotal_time_taken() {
        return total_time_taken;
    }

    public void setTotal_time_taken(String total_time_taken) {
        this.total_time_taken = total_time_taken;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }
}
