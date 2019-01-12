package com.ugo.ugodriver.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Developer on 1/23/18.
 */

public class BaseFragment extends Fragment {

    public final static String PREFS_LOCATION            = "com.gaincabs.driver";
    public final static String DRIVER_ID                 = "did";
    public final static String DRIVER_NAME               = "name";
    public final static String DRIVER_EMAIL              = "email";
    public final static String DRIVER_PHONE              = "phone";
    public final static String DRIVER_IMAGE              = "image";
    public final static String IS_LOGIN                  = "IS_LOGIN";
    public final static String AVAILABILITY              = "AVAILABILITY";

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
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


    public static String getSharedPreff(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void setSharedPref(Context context, String key, String value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putString(key,value).apply();
    }




    public static int getSharedPrefInt(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }



    public static void setSharedPrefInt(Context context, String key, int value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putInt(key,value).apply();
    }

    public static void setSharedPrefDouble(Context context,String key, float value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putFloat(key,value).apply();
    }

    public static void setSharedPrefBoolean(Context context, String key, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key,value).apply();
    }

    public static boolean getSharedPrefBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void removePreference(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }


  /*  public final static String PREFS_LOCATION            = "com.gaincabs.driver";
    public final static String DRIVER_ID                 = "did";
    public final static String DRIVER_NAME               = "name";
    public final static String DRIVER_EMAIL              = "email";
    public final static String DRIVER_PHONE              = "phone";
    public final static String DRIVER_IMAGE              = "image";
    public final static String IS_LOGIN                  = "IS_LOGIN";
    public final static String AVAILABILITY              = "AVAILABILITY";

    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        progressDialog=new ProgressDialog(getActivity());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public  void showProgressdialog(String message, Boolean cancelelable){

        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelelable);
        progressDialog.show();

    }

    public void hideProgressdialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public static String getSharedPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static boolean getSharedPrefBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static int getSharedPrefInt(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static void setSharedPref(Context context, String key, String value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putString(key,value).commit();
    }

    public static void setSharedPrefInt(Context context, String key, int value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putInt(key,value).commit();
    }

    public static void setSharedPrefDouble(Context context,String key, float value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putFloat(key,value).commit();
    }

    public static void setSharedPrefBoolean(Context context, String key, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key,value).commit();
    }*/


}
