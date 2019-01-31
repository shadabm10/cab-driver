package com.ugo.ugodriver;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ugo.ugodriver.authenticate.LoginActivity;
import com.ugo.ugodriver.Base.BaseActivity;
import com.rampo.updatechecker.UpdateChecker;
import com.rampo.updatechecker.notice.Notice;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;

/**
 * Created by Developer on 1/12/18.
 */

public class SplashScreen extends BaseActivity implements View.OnClickListener {

    TextView txt_signin;

    public static Activity fa;

    protected static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Mint.initAndStartSession(this.getApplication(), "67793b69");

        Log.d("GC load " , "entered");
        Log.d("GC load " , getSharedPrefBoolean(SplashScreen.this , IS_LOGIN)+"");
        if(getSharedPrefBoolean(SplashScreen.this , IS_LOGIN)){

            isLoginApi();

        }else{

            setContentView(R.layout.activity_splash);
            setViews();
            fa = this;
        }


        checkPermission();

        UpdateChecker checker = new UpdateChecker(this);
        checker.setNotice(Notice.NOTIFICATION);
        checker.start();


    }

    private void setViews(){
        txt_signin=(TextView)findViewById(R.id.txt_signin);
        //txt_register=(TextView)findViewById(R.id.txt_register);

        setListners();
    }

    private void setListners(){
        txt_signin.setOnClickListener(this);
       // txt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_signin:
                startActivity(new Intent(this, LoginActivity.class));
                break;

          /*  case R.id.txt_register:
                startActivity(new Intent(this, RegisterActivity.class));*/
              //  break;
        }
    }

    private void alreadyLoggedIn(){

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void checkPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {


            }else {

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("abc", "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Checking the request code of our request
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            //If permission is granted
            if(grantResults.length >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){


                //Displaying a toast
                //Toast.makeText(getActivity(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted

                checkPermission();
            }
        }
    }


    public SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        SSLContext sslContext=null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    ProgressDialog pDialog;
    public void isLoginApi(){

        final String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Checking...");
        pDialog.show();


        String url = "http://u-go.in/api/driver_check_login";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        params.put("id", getSharedPref(this, DRIVER_ID));

        Log.d("TAG" , "check_login - " + url);
        Log.d("TAG" , "check_login - " + params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        client.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TAG", "check_login- " + response.toString());

                if (response != null) {
                    try {

                        int status = response.optInt("status");
                        String message = response.optString("message");

                        if (status == 1){

                            JSONObject data = response.getJSONObject("data");

                            String login_status = data.getString("login_status");
                            String device_id = data.getString("device_id");

                            if (login_status.equals("Yes") &&
                                    android_id.equals(device_id)){


                                alreadyLoggedIn();

                            }else {

                                setContentView(R.layout.activity_splash);
                                setViews();
                                fa = SplashScreen.this;
                            }


                        }else {

                           // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                            setContentView(R.layout.activity_splash);
                            setViews();
                            fa = SplashScreen.this;

                        }

                        pDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                pDialog.dismiss();
            }

        });


    }


}
