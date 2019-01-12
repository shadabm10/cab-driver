package com.ugo.ugodriver.authenticate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface LoginApi {

//    @GET("api/abc")
//    Call<Login> login(@Query("type") String str_type);



    @FormUrlEncoded
    @POST("api/driver_login")
    Call<Login> login(@Field("email") String email,
                      @Field("password") String password,
                      @Field("device_id") String device_id,
                      @Field("fcm_reg_token") String fcm_reg_token,
                      @Field("device_type") String device_type);


}
