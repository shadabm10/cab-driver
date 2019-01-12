package com.ugo.ugodriver.fragments.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MapApi {

    @FormUrlEncoded
    @POST("api/update_latlng")
    Call<Map> update(@Field("id") String id,
                         @Field("lat") String lat,
                         @Field("lng") String lng,
                         @Field("online_offline") String online_offline
    );

    @FormUrlEncoded
    @POST("api/driver_booking_response")
    Call<Map> booking(@Field("id") String id,
                     @Field("booking_id") String lat,
                     @Field("booking_status") String lng
    );

    @FormUrlEncoded
    @POST("api/start_trip")
    Call<Map> startTrip(@Field("id") String id,
                      @Field("booking_id") String b_id,
                      @Field("otp") String otp,
                        @Field("waiting_time") String time
    );


    @FormUrlEncoded
    @POST("api/end_trip")
    Call<Map> endTrip(@Field("id") String id,
                        @Field("booking_id") String b_id,
                        @Field("drop_lat") double drop_lat,
                        @Field("drop_lng") double drop_lng

    );


}
