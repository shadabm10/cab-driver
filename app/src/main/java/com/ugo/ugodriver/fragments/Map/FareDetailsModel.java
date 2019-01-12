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
