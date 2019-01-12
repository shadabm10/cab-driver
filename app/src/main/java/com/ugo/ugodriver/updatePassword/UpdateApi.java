package com.ugo.ugodriver.updatePassword;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface UpdateApi {



    @FormUrlEncoded
    @POST("api/driver_change_password")
    Call<Update> update(@Field("id") String id,
                         @Field("oldpassword") String oldpass,
                         @Field("password") String newpass
    );



}
