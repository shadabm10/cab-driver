package com.ugo.ugodriver.forgotPassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ForgotApi {



    @FormUrlEncoded
    @POST("api/driver_forgetpass")
    Call<Forgot> forgot(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("api/driver_verify_otp")
    Call<Forgot> verify(@Field("otp") String name,
                        @Field("phone") String phone);

    @FormUrlEncoded
    @POST("api/driver_resetpass")
    Call<Forgot> reset(@Field("id") String id,
                        @Field("password") String pass);


}
