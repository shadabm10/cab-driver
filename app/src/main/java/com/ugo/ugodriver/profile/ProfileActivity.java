package com.ugo.ugodriver.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.Base.ValidationClass;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;
import com.ugo.ugodriver.updatePassword.UpdatePassword;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Developer on 1/17/18.
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    TextView change;
    ImageView back;
    EditText edt_name,edt_email,edt_mobile;
    ImageView iv_name_edit, iv_mobile_edit;
    ValidationClass validate;
    Button btn_update;
    CircleImageView profile_pic;
    Uri resultUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        validate=new ValidationClass(this);
        setViews();
    }

    private void setViews() {
        back =  findViewById(R.id.back);
        edt_name =  findViewById(R.id.edt_name);
        edt_email =  findViewById(R.id.edt_email);
        edt_mobile =  findViewById(R.id.edt_mobile);

        edt_name.setEnabled(false);
        edt_email.setEnabled(false);
        edt_mobile.setEnabled(false);

        change =  findViewById(R.id.change);
        btn_update =  findViewById(R.id.btn_update);
        profile_pic = findViewById(R.id.driver_img);
        iv_name_edit = findViewById(R.id.iv_name_edit);
        iv_mobile_edit = findViewById(R.id.iv_mobile_edit);
        setData();
        setListners();
    }

    private void setListners() {
        back.setOnClickListener(this);
        change.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        profile_pic.setOnClickListener(this);
        iv_name_edit.setOnClickListener(this);
        iv_mobile_edit.setOnClickListener(this);
    }

    private void setData(){
        edt_name.setText(getSharedPref(this,DRIVER_NAME));
        edt_email.setText(getSharedPref(this,DRIVER_EMAIL));
        edt_mobile.setText(getSharedPref(this,DRIVER_PHONE));
        edt_name.setSelection(edt_name.getText().length());
        if(!getSharedPref(this,DRIVER_IMAGE).isEmpty()){
            Picasso.with(this)
                    .load(getSharedPref(this,DRIVER_IMAGE))
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(this)
                    .into(profile_pic);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_update:
                validateUpdate();
                break;
            case R.id.change:
                startActivity(new Intent(ProfileActivity.this, UpdatePassword.class));
                break;
            case R.id.driver_img:
                startCropImageActivity();
                break;
            case R.id.iv_name_edit:
                edt_name.setEnabled(true);
                edt_name.setSelection(edt_name.length());
                edt_name.requestFocus();
                break;
            case R.id.iv_mobile_edit:
                edt_mobile.setEnabled(true);
                edt_mobile.setSelection(edt_mobile.length());
                edt_mobile.requestFocus();
                break;
        }
    }

    private void validateUpdate(){
        if (validate.validateName(edt_name)){
            if (validate.validateEmail(edt_email)){
                if (validate.validateMobileNo(edt_mobile)){
                    if (validate.checkInternetConnection(this)){

                        updateProfile();
                    }
                }
            }
        }
    }


    public void updateProfile(){

        showProgressdialog("Updating...",false);

        String url = ApiClient.BASE_URL + "api/driver_update_profile";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        params.put("id", getSharedPref(this,DRIVER_ID));
        params.put("name", edt_name.getText().toString());
        params.put("email", edt_email.getText().toString());
        params.put("phone", edt_mobile.getText().toString());

        try{

            File file = new File(resultUri.getPath());
            params.put("profile_pic", file);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("TAG" , "driver_update_profile - " + url);
        Log.d("TAG" , "driver_update_profile - " + params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        client.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TAG", "driver_update_profile- " + response.toString());

                if (response != null) {
                    try {

                        int status = response.optInt("status");
                        String message = response.optString("message");

                        if (status == 1){

                            JSONObject user_info = response.getJSONObject("user_info");

                          /*  final AlertDialog alert = new AlertDialog.Builder(ProfileActivity.this)
                                    .setMessage("Profile updated successfully.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();*/


                            edt_name.setText(user_info.getString("name"));
                            edt_email.setText(user_info.getString("email"));
                            edt_mobile.setText(user_info.getString("phone"));
                            edt_name.setSelection(edt_name.getText().length());

                            String image = user_info.getString("image");

                            Log.d("TAG", "image = " +image);

                            if(image!=null){
                                Picasso.with(ProfileActivity.this)
                                        .load(image)
                                        .placeholder(R.drawable.ic_user)
                                        .error(R.drawable.ic_user)
                                        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                                        .centerInside()
                                        .tag(this)
                                        .into(profile_pic);
                            }


                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            updatePreference(image);

                            finish();

                        }else {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        }

                        hideProgressdialog();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                hideProgressdialog();
            }

        });


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


    private void updatePreference(String pic_url){
        setSharedPref(this,DRIVER_NAME,edt_name.getText().toString());
        setSharedPref(this,DRIVER_EMAIL,edt_email.getText().toString());
        setSharedPref(this,DRIVER_PHONE,edt_mobile.getText().toString());
        setSharedPref(ProfileActivity.this,DRIVER_IMAGE,pic_url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_action_crop) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCropImageActivity() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profile_pic.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
