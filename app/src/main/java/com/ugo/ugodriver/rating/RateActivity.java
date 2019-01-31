package com.ugo.ugodriver.rating;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;
import com.ugo.ugodriver.fragments.Map.BookingModel;
import com.ugo.ugodriver.fragments.Map.FareDetailsModel;
import com.ugo.ugodriver.fragments.Map.Map;
import com.ugo.ugodriver.fragments.Map.MapApi;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer on 1/17/18.
 */

public class RateActivity extends AppCompatActivity implements View.OnClickListener {

    Button rate;
    ImageView back;
    RatingBar ratingbar;
    EditText edt_comment;
    TextView rider_name, tv_distance, tv_time, tv_fare,tv_dist, confirm_paytm_payment,tv_sub_total_fare,tv_waiting_fare,tv_commission,tv_gst_fare;
    CircleImageView rider_image;
    BaseActivity ba;
    String lat, lng;
    ProgressDialog progressDialog;
    BookingModel booking;
    FareDetailsModel fareDetailsModel;
    String payment_mode;
    CardView card_msg_paytm;
    String TAG="Rate";
    String booking_id;


    DecimalFormat precision = new DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_screen);

        setViews();

    }

    public void setViews() {

        rate =  findViewById(R.id.rate);
        tv_sub_total_fare =  findViewById(R.id.tv_sub_total_fare);
        tv_waiting_fare =  findViewById(R.id.tv_waiting_fare);
        tv_commission =  findViewById(R.id.tv_commission);
        tv_gst_fare =  findViewById(R.id.tv_gst_fare);
        back =  findViewById(R.id.back);
        ratingbar = findViewById(R.id.ratingbar);
        edt_comment = findViewById(R.id.edt_comment);
        rider_name = findViewById(R.id.rider_name);
        tv_distance = findViewById(R.id.tv_distance);
        tv_time = findViewById(R.id.tv_time);
        tv_fare = findViewById(R.id.tv_fare);
        tv_dist= findViewById(R.id.tv_dist);
        rider_image= findViewById(R.id.rider_image);
        card_msg_paytm= findViewById(R.id.card_msg_paytm);
        card_msg_paytm.setVisibility(View.GONE);
        confirm_paytm_payment = findViewById(R.id.confirm_paytm_payment);

        progressDialog=new ProgressDialog(this);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        ba = new BaseActivity();
        //booking = (BookingModel)getIntent().getSerializableExtra("booking");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        booking_id = getIntent().getStringExtra("booking_id");


        setListners();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            fareDetailsModel = (FareDetailsModel)bundle.getSerializable("data");
            payment_mode = bundle.getString("payment_mode");


            Log.d("SS", "distance - "
                    + fareDetailsModel.getTotal_distance_travelled());
            Log.d("SS", "time - "
                    + fareDetailsModel.getTotal_time_taken());
            Log.d("SS", "fare - "
                    +fareDetailsModel.getTotal_fare());
            Log.d("SS", "fare - "
                    +fareDetailsModel.getCommission_fair());
            Log.d("SS", "fare - "
                    +fareDetailsModel.getGst_fair());
            Log.d("SS", "fare - "
                    +fareDetailsModel.getWaiting_fair());
            if (payment_mode != null){

                if (payment_mode.matches("paytm")){
                    card_msg_paytm.setVisibility(View.VISIBLE);
                }else if (payment_mode.matches("cash")){
                    card_msg_paytm.setVisibility(View.GONE);
                }

            }



            tv_distance.setText(fareDetailsModel.getTotal_distance_travelled()+" KM");
            tv_dist.setText(fareDetailsModel.getTotal_distance_travelled()+" KM");
            tv_time.setText(secondMinHour(Integer.parseInt(fareDetailsModel.getTotal_time_taken())));

            double totalFare = Double.parseDouble(fareDetailsModel.getTotal_fare());
            double sub_total_fare = Double.parseDouble(fareDetailsModel.getSub_total_fare());
            double waiting_fair = Double.parseDouble(fareDetailsModel.getWaiting_fair());
            double commission_fair = Double.parseDouble(fareDetailsModel.getCommission_fair());
            double gst_fair = Double.parseDouble(fareDetailsModel.getGst_fair());
            String stringdouble= Double.toString(totalFare);
            String sub_total_fare1= Double.toString(sub_total_fare);
            String waiting_fair1= Double.toString(waiting_fair);
            String commission_fair1= Double.toString(commission_fair);
            String gst_fair1= Double.toString(gst_fair);
            tv_fare.setText("₹ "+stringdouble);
           tv_sub_total_fare.setText("₹ "+sub_total_fare1);
            tv_waiting_fare.setText("₹ "+waiting_fair1);
            tv_commission.setText("₹ "+commission_fair1);
            tv_gst_fare.setText("₹ "+gst_fair1);

        }

    }

    public void setListners() {
        rate.setOnClickListener(this);
       // back.setOnClickListener(this);
        confirm_paytm_payment.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       // updateLatlng();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rate:
                if(ratingbar.getRating()!=0.0) {

                    if(!edt_comment.getText().toString().isEmpty()) {
                        sendFeedback(String.valueOf(ratingbar.getRating()) , edt_comment.getText().toString());

                    }else{
                        sendFeedback(String.valueOf(ratingbar.getRating()) , "");
                    }

                }else{
                    Toast.makeText(RateActivity.this,
                            "Rate customer to complete the trip" ,
                            Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.confirm_paytm_payment:

                paytmPaymentConfirmation();

                break;

        }
    }

    public void updateLatlng(){
        Log.d("SS", "update latlng called ");
        progressDialog.setMessage("Processing ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if(ba.getSharedPref(this,ba.DRIVER_ID)!=null) {
            MapApi apiService = ApiClient.getClient().create(MapApi.class);
            Log.d("SS", "id: " + ba.getSharedPref(this, ba.DRIVER_ID) + "");
            Log.d("SS", "lat: " + lat + " " + lng);
            Call<Map> call = apiService.update(ba.getSharedPref(this, ba.DRIVER_ID)
                    , lat.toString()
                    , lng.toString()
                    , "1"
            );
            call.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    Log.d("SS", "Raw1: ");
                    progressDialog.dismiss();

                    try {

                        if (response != null) {
                            if (response.body().getBooking_info() != null){
                                booking = response.body().getBooking_info();
                                rider_name.setText(booking.getCustomer_name());

                                Picasso.with(RateActivity.this)
                                        .load(booking.getCustomer_image())
                                        .placeholder(R.drawable.ic_user)
                                        .error(R.drawable.ic_user)
                                        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                                        .centerInside()
                                        .tag(this)
                                        .into(rider_image);

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Log.d("SS", "onResponse: " + t.getMessage());
                    //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private String secondMinHour(int i){
        long hours = i / 3600;
        long minutes = (i % 3600) / 60;
        long seconds = i % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public void sendFeedback(String rate , String comment){
        progressDialog.setMessage("Sending ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RateApi apiService = ApiClient.getClient().create(RateApi.class);
            Log.d("TAG", "id: " + ba.getSharedPref(this, ba.DRIVER_ID) + "");
            Log.d("TAG", "lat: " + lat + " " + lng);
            Log.d("TAG", "booking_id: " +booking_id);

            Call<RateModel> call = apiService.sendFeedback(ba.getSharedPref(this, ba.DRIVER_ID)
                    , booking_id
                    , rate
                    , comment
            );

            call.enqueue(new Callback<RateModel>() {
                @Override
                public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                    try {
                        Log.d("TAG", "Raw rating: "+response);

                        if (response != null) {
                            if (response.body().getStatus() == 1 ) {

                                Intent intent = new Intent();
                                intent.putExtra("drop", "dropped");
                                Log.d(TAG, "rating success: "+response.body().getStatus());
                                setResult(Activity.RESULT_OK, intent);


                                progressDialog.dismiss();

                                finish();

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<RateModel> call, Throwable t) {
                    Log.d("LOG", "onResponse: " + t.getMessage());
                    //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


    public void paytmPaymentConfirmation(){

        progressDialog.setMessage("Sending ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RateApi apiService = ApiClient.getClient().create(RateApi.class);
        Log.d("TAG", "Booking id: " + booking_id);

        Call<RateModel> call = apiService.paytm_payment_confirmation(booking_id);

        call.enqueue(new Callback<RateModel>() {
            @Override
            public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                Log.d("TAG", "Raw1: ");
                progressDialog.dismiss();

                try {
                    if (response != null) {
                        if (response.body().getStatus() == 1 ) {

                            card_msg_paytm.setVisibility(View.GONE);
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RateModel> call, Throwable t) {
                Log.d("LOG", "onResponse: " + t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

}
