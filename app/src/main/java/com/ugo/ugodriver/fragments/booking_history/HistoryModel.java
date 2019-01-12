package com.ugo.ugodriver.fragments.booking_history;

import com.ugo.ugodriver.fragments.Map.BookingModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ANDROID on 2/1/2018.
 */

public class HistoryModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("booking_Details")
    private ArrayList<BookingModel> booking_Details;

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

    public ArrayList<BookingModel> getBooking_Details() {
        return booking_Details;
    }

    public void setBooking_Details(ArrayList<BookingModel> booking_Details) {
        this.booking_Details = booking_Details;
    }
}
