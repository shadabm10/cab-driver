package com.ugo.ugodriver.fragments.myEarning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ANDROID on 2/15/2018.
 */

public class MyEarning extends Fragment {

    TextView tv_date, tv_total_price, tv_p, tv_award, tv_promotion;
    TextView tv_total_pri, tv_time, tv_trip, tv_guider_fees;
    RelativeLayout rl_main;
    String to_date;

    BaseActivity ba;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_earning, container, false);

        initViews(view);



        return view;
    }

    private void initViews(View view){

        ba = new BaseActivity();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");


        rl_main = view.findViewById(R.id.rl_main);
        rl_main.setVisibility(View.INVISIBLE);

        tv_date = view.findViewById(R.id.tv_date);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        tv_p = view.findViewById(R.id.tv_p);
        tv_award = view.findViewById(R.id.tv_award);
        tv_promotion = view.findViewById(R.id.tv_promotion);
        tv_total_pri = view.findViewById(R.id.tv_total_pri);
        tv_time = view.findViewById(R.id.tv_time);
        tv_trip = view.findViewById(R.id.tv_trip);
        tv_guider_fees = view.findViewById(R.id.tv_guider_fees);

        Date curDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd/MMM/yyyy");
        String DateToStr1 = format1.format(curDate);
        System.out.println(DateToStr1);
        to_date = DateToStr1;

        String DateToStr2 = format2.format(curDate);
        tv_date.setText("Date : "+DateToStr2);


        getEarning();

    }


    private void getEarning(){

        progressDialog.show();

        final DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        MyEarningApi apiService = ApiClient.getClient().create(MyEarningApi.class);
        Call<EarningResponce> call = apiService.myEarning(ba.getSharedPref(getActivity(),ba.DRIVER_ID)
                ,to_date
        );
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));

        call.enqueue(new Callback<EarningResponce>() {
            @Override
            public void onResponse(Call<EarningResponce>call, Response<EarningResponce> response) {

                try{
                    if(response != null){

                        if (response.body().getStatus() == 1){

                            // tv_date.setText("Date : "+response.body().getEarning_info().getDate());

                            tv_p.setText(""+response.body().getEarning_info().getPrice());

                            tv_award.setText(""+response.body().getEarning_info().getReferral_award());
                            tv_promotion.setText(""+response.body().getEarning_info().getPromotions());
                            tv_guider_fees.setText(""+response.body().getEarning_info().getGuide_charge());


                            double total_ = response.body().getEarning_info().getPrice()
                                    + response.body().getEarning_info().getReferral_award()
                                    + response.body().getEarning_info().getPromotions()
                                    + response.body().getEarning_info().getGuide_charge();

                            tv_total_pri.setText(String.valueOf(df.format(total_)));

                            tv_time.setText(secondMinHour(response.body().getEarning_info().getTotal_time_in_sec()));
                            tv_trip.setText(""+response.body().getEarning_info().getTotal_trips());


                            rl_main.setVisibility(View.VISIBLE);

                        }else {

                            Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_LONG).show();
                        }


                        progressDialog.dismiss();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<EarningResponce> call, Throwable t) {
                Log.d("LOG", "onResponse: "+t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private String secondMinHour(long i){
        long hours = i / 3600;
        long minutes = (i % 3600) / 60;
        long seconds = i % 60;
        String time = String.format("%02d:%02d", hours, minutes);
        return time;
    }

}
