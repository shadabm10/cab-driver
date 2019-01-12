package com.ugo.ugodriver.profile;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileApi {
    @Multipart
    @POST("api/driver_update_profile")
    Call<Profile> update(@Part MultipartBody.Part image,
                         @Part("id") RequestBody id,
                           @Part("name") RequestBody  name,
                           @Part("email") RequestBody  email,
                           @Part("phone") RequestBody  phone
                            );



}
