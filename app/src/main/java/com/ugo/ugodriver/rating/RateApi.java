package com.ugo.ugodriver.rating;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ANDROID on 2/14/2018.
 */

public interface RateApi {

    @FormUrlEncoded
    @POST("api/driver_feedback")
    Call<RateModel> sendFeedback(@Field("id") String id,
                        @Field("booking_id") String booking_id,
                        @Field("rate") String rate,
                        @Field("comment") String comment
    );




    @FormUrlEncoded
    @POST("api/paytm_payment_confirmation")
    Call<RateModel> paytm_payment_confirmation(@Field("booking_id") String booking_id);



}
