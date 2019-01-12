package com.ugo.ugodriver.forgotPassword;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ForgotPassActivity extends BaseActivity implements View.OnClickListener{

    Button verify;
    Dialog dialogverify;
    EditText otp,edt_mobile;
    ValidationClass validate;
    String driverid;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        validate=new ValidationClass(this);
        setViews();
    }
    private void setViews(){

        verify=(Button)findViewById(R.id.verify);
        back=(ImageView) findViewById(R.id.back);
        edt_mobile=(EditText) findViewById(R.id.edt_mobile);
        setListners();
    }

    private void setListners(){
      verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.verify:

                if (validate.validateMobileNo(edt_mobile))
                {
                    if (validate.checkInternetConnection(this)){
                        forgot(edt_mobile.getText().toString());
                    }
                }

                break;

            case R.id.back:
                finish();
                break;
        }
    }


    public void forgot(final String mobile) {

        showProgressdialog("Verifying...", false);
        ForgotApi apiService = ApiClient.getClient().create(ForgotApi.class);

        Call<Forgot> call = apiService.forgot(mobile);
        call.enqueue(new Callback<Forgot>() {


            @Override
            public void onResponse(Call<Forgot> call, Response<Forgot> response) {


                if (response != null) {

                    if (response.body().getStatus() == 1) {

                        driverid=response.body().getId();
                       verifyDialog(response.body().getOtp());

                    } else {
                        Toast.makeText(ForgotPassActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Forgot> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: " + t.getMessage());
                Toast.makeText(ForgotPassActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void verifyDialog(String totp) {
        dialogverify = new Dialog(ForgotPassActivity.this);
        dialogverify.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogverify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogverify.setContentView(R.layout.verify);
        dialogverify.setCancelable(true);
        dialogverify.setCanceledOnTouchOutside(false);
        dialogverify.show();
        TextView shownumber = (TextView) dialogverify.findViewById(R.id.show);
        shownumber.setText(edt_mobile.getText().toString());
        otp = (EditText) dialogverify.findViewById(R.id.otp);

        otp.setText(totp);

        Button verify = (Button) dialogverify.findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp.getText().length()==6) {

                    verifyOtp();
                }
                else{
                    Toast.makeText(ForgotPassActivity.this, "Enter 6 digit otp", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void verifyOtp(){
        showProgressdialog("Verifying...",false);
        ForgotApi apiService = ApiClient.getClient().create(ForgotApi.class);
        Call<Forgot> call = apiService.verify(otp.getText().toString()
                ,edt_mobile.getText().toString()
        );
        call.enqueue(new Callback<Forgot>() {


            @Override
            public void onResponse(Call<Forgot>call, Response<Forgot> response) {


                if(response != null){

                    if (response.body().getStatus()==1){
                        dialogverify.dismiss();

                        Intent intent =new Intent(ForgotPassActivity.this,ResetPassword.class);
                        intent.putExtra("id",driverid);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(ForgotPassActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }


                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Forgot> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(ForgotPassActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
