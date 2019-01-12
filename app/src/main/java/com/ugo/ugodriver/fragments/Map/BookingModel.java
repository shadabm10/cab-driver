package com.ugo.ugodriver.fragments.Map;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ANDROID on 1/31/2018.
 */

public class BookingModel implements Serializable{

    @SerializedName("id")
    private String booking_id;

    @SerializedName("uid")
    private String cust_id;

    @SerializedName("did")
    private String driver_id;

    @SerializedName("invoice_number")
    private String invoice_number;

    @SerializedName("otp")
    private String otp;

    @SerializedName("pick_address")
    private String pick_address;

    @SerializedName("pick_lat")
    private String pick_lat;

    @SerializedName("pick_lng")
    private String pick_lng;

    @SerializedName("drop_address")
    private String drop_address;

    @SerializedName("drop_lat")
    private String drop_lat;

    @SerializedName("drop_lng")
    private String drop_lng;

    @SerializedName("cancel_by")
    private String cancel_by;

    @SerializedName("cancel_reason")
    private String cancel_reason;

    @SerializedName("driver_alloted_time")
    private String driver_alloted_time;

    @SerializedName("booking_status")
    private String booking_status;

    @SerializedName("booking_km")
    private String booking_km;

    @SerializedName("booking_total_time")
    private String booking_total_time;

    @SerializedName("driver_waiting_time")
    private String driver_waiting_time;

    @SerializedName("trip_start_time")
    private String trip_start_time;

    @SerializedName("trip_end_time")
    private String trip_end_time;

    @SerializedName("total_fare")
    private String total_fare;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("customer_image")
    private String customer_image;

    @SerializedName("customer_phone")
    private String customer_phone;

    @SerializedName("booking_type")
    private String booking_type;

    @SerializedName("coupon_applied")
    private String coupon_applied;

    @SerializedName("coupon_code")
    private String coupon_code;

    @SerializedName("coupon_amount")
    private String coupon_amount;

    @SerializedName("customer_rate")
    private int customer_rate;

    @SerializedName("guide_charges")
    private float guide_charges;

    @SerializedName("gst_rate")
    private float gst_rate;

    @SerializedName("guide")
    private String guide;

    @SerializedName("payment_mode")
    private String payment_mode;

    @SerializedName("rejected_by_driver")
    private String rejected_by_driver;





    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPick_address() {
        return pick_address;
    }

    public void setPick_address(String pick_address) {
        this.pick_address = pick_address;
    }

    public String getPick_lat() {
        return pick_lat;
    }

    public void setPick_lat(String pick_lat) {
        this.pick_lat = pick_lat;
    }

    public String getPick_lng() {
        return pick_lng;
    }

    public void setPick_lng(String pick_lng) {
        this.pick_lng = pick_lng;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public void setDrop_address(String drop_address) {
        this.drop_address = drop_address;
    }

    public String getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(String drop_lat) {
        this.drop_lat = drop_lat;
    }

    public String getDrop_lng() {
        return drop_lng;
    }

    public void setDrop_lng(String drop_lng) {
        this.drop_lng = drop_lng;
    }

    public String getCancel_by() {
        return cancel_by;
    }

    public void setCancel_by(String cancel_by) {
        this.cancel_by = cancel_by;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getDriver_alloted_time() {
        return driver_alloted_time;
    }

    public void setDriver_alloted_time(String driver_alloted_time) {
        this.driver_alloted_time = driver_alloted_time;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getBooking_km() {
        return booking_km;
    }

    public void setBooking_km(String booking_km) {
        this.booking_km = booking_km;
    }

    public String getBooking_total_time() {
        return booking_total_time;
    }

    public void setBooking_total_time(String booking_total_time) {
        this.booking_total_time = booking_total_time;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }

    public String getDriver_waiting_time() {
        return driver_waiting_time;
    }

    public void setDriver_waiting_time(String driver_waiting_time) {
        this.driver_waiting_time = driver_waiting_time;
    }

    public String getTrip_start_time() {
        return trip_start_time;
    }

    public void setTrip_start_time(String trip_start_time) {
        this.trip_start_time = trip_start_time;
    }

    public String getTrip_end_time() {
        return trip_end_time;
    }

    public void setTrip_end_time(String trip_end_time) {
        this.trip_end_time = trip_end_time;
    }

    public String getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(String booking_type) {
        this.booking_type = booking_type;
    }


    public String getCoupon_applied() {
        return coupon_applied;
    }

    public void setCoupon_applied(String coupon_applied) {
        this.coupon_applied = coupon_applied;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public int getCustomer_rate() {
        return customer_rate;
    }

    public void setCustomer_rate(int customer_rate) {
        this.customer_rate = customer_rate;
    }

    public float getGuide_charges() {
        return guide_charges;
    }

    public void setGuide_charges(float guide_charges) {
        this.guide_charges = guide_charges;
    }

    public float getGst_rate() {
        return gst_rate;
    }

    public void setGst_rate(float gst_rate) {
        this.gst_rate = gst_rate;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getRejected_by_driver() {
        return rejected_by_driver;
    }

    public void setRejected_by_driver(String rejected_by_driver) {
        this.rejected_by_driver = rejected_by_driver;
    }
}
