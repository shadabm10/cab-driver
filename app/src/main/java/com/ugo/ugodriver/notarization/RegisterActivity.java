package com.ugo.ugodriver.notarization;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.Base.ValidationClass;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer on 1/12/18.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    Button btn_signup;
    ValidationClass validate;
    Dialog dialogverify;
    EditText otp;
    EditText edt_name, edt_email, edt_mobile, edt_pass, edt_cpass, edt_city;
    TextView tv_resend_otp, tv_show_timer;


    private Handler handler = new Handler();
    private Runnable runnable;
    private long milliSeconds = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        validate = new ValidationClass(this);
        setViews();
    }

    private void setViews() {
        btn_signup = (Button) findViewById(R.id.btn_signup);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        edt_cpass = (EditText) findViewById(R.id.edt_cpass);
        edt_city = (EditText) findViewById(R.id.edt_city);


        setListners();

    }

    private void setListners() {
        btn_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                validateEntry();
                //startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    public void validateEntry() {
        if (validate.validateName(edt_name)) {
            if (validate.validateEmail(edt_email)) {
                if (validate.validateMobileNo(edt_mobile)) {
                    if (validate.validatePassword1(edt_pass)) {
                        if (validate.validatePassword2(edt_pass, edt_cpass)) {
                            if (validate.validateCity(edt_city)){
                                if (validate.checkInternetConnection(this)) {
                                    register();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void register(){
        showProgressdialog("Enrolling...",false);
        RegisterApi apiService = ApiClient.getClient().create(RegisterApi.class);
        Call<Register> call = apiService.register(edt_name.getText().toString()
                ,edt_email.getText().toString()
                ,edt_mobile.getText().toString()
                ,edt_pass.getText().toString()
                ,validate.getAndroid_id()
                ,validate.getFcm_token()
                ,"ANDROID"
                ,edt_city.getText().toString()
        );
        call.enqueue(new Callback<Register>() {

            @Override
            public void onResponse(Call<Register>call, Response<Register> response) {


                if(response != null){

                    if (response.body().getStatus()==1){

                        Log.d("TAG", "OTP = "+response.body().getOtp());


                        verifyDialog(response.body().getOtp());
                    }else{
                        Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(RegisterActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void verifyDialog(String totp) {
        dialogverify = new Dialog(RegisterActivity.this);
        dialogverify.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogverify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogverify.setContentView(R.layout.verify);
        dialogverify.setCancelable(false);
        dialogverify.setCanceledOnTouchOutside(false);
        dialogverify.show();
        TextView shownumber = (TextView) dialogverify.findViewById(R.id.show);
        shownumber.setText(edt_mobile.getText().toString());
        otp = (EditText) dialogverify.findViewById(R.id.otp);

        //otp.setText(totp);

        tv_resend_otp = (TextView) dialogverify.findViewById(R.id.tv_resend_otp);
        tv_show_timer = (TextView) dialogverify.findViewById(R.id.tv_show_timer);

        Button verify = (Button) dialogverify.findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp.getText().length() == 6) {

                    verifyOtp();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Enter 6 digit otp", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tv_resend_otp.setVisibility(View.GONE);
        tv_show_timer.setVisibility(View.VISIBLE);
        timerStart();

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_resend_otp.setVisibility(View.GONE);
                tv_show_timer.setVisibility(View.VISIBLE);

                resendOtp();

            }
        });


    }



    private void timerStart() {
        milliSeconds = 0;

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                milliSeconds = milliSeconds + 1000;

                int hours = (int) ((milliSeconds / (1000 * 60 * 60)) % 24);
                int minutes = (int) ((milliSeconds / (1000 * 60)) % 60);
                int seconds = (int) (milliSeconds / 1000) % 60;

                /*tv_show_timer.setText("Wait for "+String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));*/


                tv_show_timer.setText("Wait "
                        + ": " + String.format("%02d", seconds) + " sec");


                if (seconds == 30){
                    handler.removeCallbacks(runnable);
                    tv_resend_otp.setVisibility(View.VISIBLE);
                    tv_show_timer.setVisibility(View.GONE);
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }


    public void verifyOtp(){
        showProgressdialog("Verifying...",false);
        RegisterApi apiService = ApiClient.getClient().create(RegisterApi.class);
        Call<Verify> call = apiService.verify(otp.getText().toString()
                ,edt_mobile.getText().toString()
        );
        call.enqueue(new Callback<Verify>() {


            @Override
            public void onResponse(Call<Verify>call, Response<Verify> response) {


                if(response != null){

                    if (response.body().getStatus()==1){
                        dialogverify.dismiss();
                        finish();
                        //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }


                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Verify> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(RegisterActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void resendOtp(){

        showProgressdialog("Resending...",false);
        RegisterApi apiService = ApiClient.getClient().create(RegisterApi.class);
        Call<Verify> call = apiService.driver_resend_otp(edt_mobile.getText().toString()
        );
        call.enqueue(new Callback<Verify>() {
            @Override
            public void onResponse(Call<Verify>call, Response<Verify> response) {

                if(response != null){

                    if (response.body().getStatus()==1){

                        Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                        timerStart();

                        //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Verify> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(RegisterActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
