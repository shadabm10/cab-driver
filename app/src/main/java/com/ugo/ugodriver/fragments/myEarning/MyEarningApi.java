package com.ugo.ugodriver.fragments.myEarning;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by developer on 2/3/18.
 */

public interface MyEarningApi {

    @FormUrlEncoded
    @POST("api/driver_earnings")
    Call<EarningResponce> myEarning(@Field("id") String id,
                        @Field("today_date") String today_date);


}
