package com.ugo.ugodriver.notarization;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RegisterApi {



    @FormUrlEncoded
    @POST("api/driver_registration")
    Call<Register> register(@Field("name") String name,
                      @Field("email") String email,
                      @Field("phone") String phone,
                      @Field("password") String password,
                      @Field("device_id") String device_id,
                      @Field("fcm_reg_token") String fcm_reg_token,
                      @Field("device_type") String device_type,
                      @Field("address") String address);

    @FormUrlEncoded
    @POST("api/driver_verify_otp")
    Call<Verify> verify(@Field("otp") String name,
                      @Field("phone") String phone);


    @FormUrlEncoded
    @POST("api/driver_resend_otp")
    Call<Verify> driver_resend_otp(@Field("phone") String phone);


}
