package com.ugo.ugodriver.cancel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ANDROID on 2/6/2018.
 */

public interface CancelApi {
    @FormUrlEncoded
    @POST("api/driver_action")
    Call<CancelModel> cancelBooking(@Field("id") String id,
                      @Field("booking_id") String bid,
                      @Field("booking_status") String b_status,
                      @Field("cancel_reason") String reason
    );
}
