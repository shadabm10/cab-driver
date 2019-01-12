package com.ugo.ugodriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ugo.ugodriver.authenticate.LoginActivity;
import com.ugo.ugodriver.Base.BaseActivity;
import com.rampo.updatechecker.UpdateChecker;
import com.rampo.updatechecker.notice.Notice;
import com.splunk.mint.Mint;

/**
 * Created by Developer on 1/12/18.
 */

public class SplashScreen extends BaseActivity implements View.OnClickListener {

    TextView txt_signin;

    public static Activity fa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Mint.initAndStartSession(this.getApplication(), "67793b69");

        Log.d("GC load " , "entered");
        Log.d("GC load " , getSharedPrefBoolean(SplashScreen.this , IS_LOGIN)+"");
        if(getSharedPrefBoolean(SplashScreen.this , IS_LOGIN)){
            alreadyLoggedIn();
        }else{
            setContentView(R.layout.activity_splash);
            setViews();
            fa = this;
        }


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
}
