package com.ugo.ugodriver.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Developer on 1/23/18.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String PREFS_LOCATION            = "com.gaincabs.driver";
    public static final String DRIVER_ID                 = "did";
    public static final String DRIVER_NAME               = "name";
    public static final String DRIVER_EMAIL              = "email";
    public static final String DRIVER_PHONE              = "phone";
    public static final String DRIVER_IMAGE              = "image";
    public static final String IS_LOGIN                  = "IS_LOGIN";
    public static final String AVAILABILITY              = "AVAILABILITY";
    public static final String CONTINUE_TIME             = "continue_time";
    public static final String DRIVER_device_type             = "device_type";
    public static final String DRIVER_fcm_reg_token             = "fcm_reg_token";
    public static final String DRIVER_deviceid             = "deviceid";
    public static final String DRIVER_RATING             = "driver_rating";

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    public  void showProgressdialog(String message,Boolean cancelelable){

        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelelable);
        progressDialog.show();

    }

    public void hideProgressdialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public String getSharedPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public void setSharedPref(Context context, String key, String value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putString(key,value).apply();
    }




    public int getSharedPrefInt(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public float getSharedPrefFloat(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getFloat(key, 0);
    }

    public void setSharedPrefFloat(Context context, String key, float value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putFloat(key,value).apply();
    }

    public void setSharedPrefInt(Context context, String key, int value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putInt(key,value).apply();
    }

    public void setSharedPrefDouble(Context context,String key, float value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putFloat(key,value).apply();
    }

    public void setSharedPrefBoolean(Context context, String key, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key,value).apply();
    }

    public boolean getSharedPrefBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public void removePreference(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }


}
