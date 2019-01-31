package com.ugo.ugodriver.authenticate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.rampo.updatechecker.UpdateChecker;
import com.rampo.updatechecker.notice.Notice;
import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.Base.ValidationClass;
import com.ugo.ugodriver.LocationPermission;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;
import com.ugo.ugodriver.SplashScreen;
import com.ugo.ugodriver.forgotPassword.ForgotPassActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Developer on 1/12/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    Button btn_signin;
    TextView txt_forgot;
    ValidationClass validate;
    EditText edt_email,edt_password;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        validate = new ValidationClass(this);
        setViews();


        UpdateChecker checker = new UpdateChecker(this);
        checker.setNotice(Notice.NOTIFICATION);
        checker.start();


    }
    private void setViews(){
        btn_signin= findViewById(R.id.btn_signin);
        txt_forgot= findViewById(R.id.txt_forgot);
        edt_email= findViewById(R.id.edt_email);
        edt_password= findViewById(R.id.edt_password);

        setListners();

        loginActivity = this;

        updateAndroidSecurityProvider(loginActivity);
    }

    private void setListners(){
        btn_signin.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signin:
                validateEntry();
                break;
            case R.id.txt_forgot:
                startActivity(new Intent(this, ForgotPassActivity.class));
                break;
        }
    }


    public void validateEntry(){
        if (validate.validateEmail(edt_email))
        {
            if (validate.validatePassword1(edt_password)){

                if (validate.checkInternetConnection(this)) {
                    login(edt_email.getText().toString(), edt_password.getText().toString());
                }else {
                    Toast.makeText(LoginActivity.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }

    public void login(final String str_username, String str_password){

        showProgressdialog("Logging In...",false);
        LoginApi apiService = ApiClient.getClient().create(LoginApi.class);
        Call<Login> call = apiService.login(str_username,str_password,validate.getAndroid_id(),validate.getFcm_token(),"ANDROID");
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login>call, Response<Login> response) {
                if(response != null){
                    if (response.body().getStatus()==1){
                        if(response.body().getDriver_info().getApproval_status().equals("1")) {
                            setSharedPrefBoolean(LoginActivity.this, IS_LOGIN, true);
                            setSharedPref(LoginActivity.this, DRIVER_ID, response.body().getDriver_info().getUid());
                            setSharedPref(LoginActivity.this, DRIVER_NAME, response.body().getDriver_info().getName());
                            setSharedPref(LoginActivity.this, DRIVER_EMAIL, response.body().getDriver_info().getEmail());
                            setSharedPref(LoginActivity.this, DRIVER_PHONE, response.body().getDriver_info().getPhone());
                            setSharedPref(LoginActivity.this, DRIVER_IMAGE, response.body().getDriver_info().getImage());
                            setSharedPref(LoginActivity.this, DRIVER_fcm_reg_token, response.body().getDriver_info().getFcm_reg_token());
                            setSharedPref(LoginActivity.this, DRIVER_device_type, response.body().getDriver_info().getDevice_type());
                            setSharedPref(LoginActivity.this, DRIVER_deviceid, response.body().getDriver_info().getDeviceid());
                            setSharedPrefFloat(LoginActivity.this, DRIVER_RATING, Float.parseFloat(response.body().getDriver_info().getDriver_rating()));
                            Intent i = new Intent(LoginActivity.this, LocationPermission.class);
                            startActivity(i);
                            SplashScreen.fa.finish();
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"You are not a UGO Driver yet.",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                hideProgressdialog();
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onFailure: "+t.getMessage());
                Toast.makeText(LoginActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
