package com.ugo.ugodriver.forgotPassword;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.Base.ValidationClass;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer on 1/17/18.
 */

public class ResetPassword extends BaseActivity implements OnClickListener {

    EditText newpassword,confirmpassword;
    Button update;
    ValidationClass validate;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.resetpassword);

        validate=new ValidationClass(this);
        setViews();
    }
    public void setViews(){
        newpassword=(EditText)findViewById(R.id.newpassword);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        update=(Button)findViewById(R.id.update);
        back=(ImageView)findViewById(R.id.back);

        setListner();
    }

    public void setListner(){
        update.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update:

                if (validate.validatePassword1(newpassword))
                {
                    if (validate.validatePassword2(newpassword,confirmpassword)) {
                        if (validate.checkInternetConnection(this)) {
                            reset(newpassword.getText().toString());
                        }
                    }
                }

                break;
            case R.id.back:
                finish();
                break;
        }
    }


    public void reset(String pass){
        showProgressdialog("Updating...",false);
        ForgotApi apiService = ApiClient.getClient().create(ForgotApi.class);
        Call<Forgot> call = apiService.reset(getIntent().getStringExtra("id")
                ,pass
        );
        call.enqueue(new Callback<Forgot>() {


            @Override
            public void onResponse(Call<Forgot>call, Response<Forgot> response) {


                if(response != null){

                    if (response.body().getStatus()==1){

                        AlertDialog alert = new AlertDialog.Builder(ResetPassword.this)
                                .setMessage("Password updated successfully.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();


                    }else{
                        Toast.makeText(ResetPassword.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                hideProgressdialog();
            }

            @Override
            public void onFailure(Call<Forgot> call, Throwable t) {
                hideProgressdialog();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(ResetPassword.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
