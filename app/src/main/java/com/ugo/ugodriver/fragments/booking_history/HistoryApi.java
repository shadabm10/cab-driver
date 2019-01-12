package com.ugo.ugodriver.fragments.booking_history;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ANDROID on 2/1/2018.
 */

public interface HistoryApi {
    @FormUrlEncoded
    @POST("api/get_driver_booking_history")
    Call<HistoryModel> getHistory(@Field("id") String id
    );

}
