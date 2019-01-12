package com.ugo.ugodriver.cancel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 2/6/2018.
 */

public class CancelModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

  /*  @SerializedName("booking_Details")
    private ArrayList<BookingModel> booking_Details;*/

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
