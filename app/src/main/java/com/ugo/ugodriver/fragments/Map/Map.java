package com.ugo.ugodriver.fragments.Map;

import com.google.gson.annotations.SerializedName;

public class Map {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("booking_info")
    private BookingModel booking_info;

    @SerializedName("fare_Details")
    private FareDetailsModel fare_Details;

    @SerializedName("plan_details")
    private PlanDetailsModel plan_details;





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

    public BookingModel getBooking_info() {
        return booking_info;
    }

    public void setBooking_info(BookingModel booking_info) {
        this.booking_info = booking_info;
    }


    public PlanDetailsModel getPlan_details() {
        return plan_details;
    }

    public void setPlan_details(PlanDetailsModel plan_details) {
        this.plan_details = plan_details;
    }

    public FareDetailsModel getFare_Details() {
        return fare_Details;
    }

    public void setFare_Details(FareDetailsModel fare_Details) {
        this.fare_Details = fare_Details;
    }
}
