package com.ugo.ugodriver.otp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.fragments.Map.WaitingTimeService;
import com.mukesh.OtpView;

/**
 * Created by Developer on 1/17/18.
 */

public class EnterOtp extends AppCompatActivity implements View.OnClickListener {

    Button confirm;
    ImageView back;
    OtpView otpview;
    BaseActivity ba;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_otp);
        setViews();
    }

    public void setViews() {
        confirm =  findViewById(R.id.confirm);
        back =  findViewById(R.id.back);
        otpview = findViewById(R.id.otp_view);
        ba = new BaseActivity();
        setListners();
    }

    public void setListners() {
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if(otpview.hasValidOTP()) {
                    stopServiceClass();
                    Intent intent = new Intent();
                    intent.putExtra("otp", otpview.getOTP());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(EnterOtp.this , "Enter OTP" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    // Method to stop the service
    public void stopServiceClass() {
        stopService(new Intent(EnterOtp.this, WaitingTimeService.class));
        ba.setSharedPrefInt(this, ba.CONTINUE_TIME, 0);
    }

}
