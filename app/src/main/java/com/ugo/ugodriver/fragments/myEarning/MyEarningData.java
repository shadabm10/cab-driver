package com.ugo.ugodriver.fragments.myEarning;

import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 2/3/18.
 */

public class MyEarningData {

    @SerializedName("date")
    private String date;

    @SerializedName("price")
    private double price;

    @SerializedName("referral_award")
    private double referral_award;

    @SerializedName("promotions")
    private double promotions;

    @SerializedName("guide_charge")
    private double guide_charge;

    @SerializedName("commission_rate")
    private double commission_rate;

    @SerializedName("gst_rate")
    private double gst_rate;

    @SerializedName("total_time_in_sec")
    private long total_time_in_sec;

    @SerializedName("total_trips")
    private int total_trips;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getReferral_award() {
        return referral_award;
    }

    public void setReferral_award(double referral_award) {
        this.referral_award = referral_award;
    }

    public double getPromotions() {
        return promotions;
    }

    public void setPromotions(double promotions) {
        this.promotions = promotions;
    }

    public double getGuide_charge() {
        return guide_charge;
    }

    public void setGuide_charge(double guide_charge) {
        this.guide_charge = guide_charge;
    }

    public double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(double commission_rate) {
        this.commission_rate = commission_rate;
    }

    public double getGst_rate() {
        return gst_rate;
    }

    public void setGst_rate(double gst_rate) {
        this.gst_rate = gst_rate;
    }

    public long getTotal_time_in_sec() {
        return total_time_in_sec;
    }

    public void setTotal_time_in_sec(long total_time_in_sec) {
        this.total_time_in_sec = total_time_in_sec;
    }

    public int getTotal_trips() {
        return total_trips;
    }

    public void setTotal_trips(int total_trips) {
        this.total_trips = total_trips;
    }
}
