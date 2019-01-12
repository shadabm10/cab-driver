package com.ugo.ugodriver.cancel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;

/**
 * Created by Developer on 1/17/18.
 */

public class CancellationReason extends BaseActivity implements View.OnClickListener {

    RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7, rl8;
    ImageView icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, back;
    Button confirm;
    String reason;
    EditText edt_reason;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_reason);

        setViews();
    }

    public void setViews() {

        rl1 =  findViewById(R.id.rl1);
        rl2 =  findViewById(R.id.rl2);
        rl3 =  findViewById(R.id.rl3);
        rl4 =  findViewById(R.id.rl4);
        rl5 =  findViewById(R.id.rl5);
        rl6 =  findViewById(R.id.rl6);
        rl7 =  findViewById(R.id.rl7);
        rl8 =  findViewById(R.id.rl8);
        back =  findViewById(R.id.back);
        icon1 =  findViewById(R.id.icon1);
        icon2 =  findViewById(R.id.icon2);
        icon3 =  findViewById(R.id.icon3);
        icon4 =  findViewById(R.id.icon4);
        icon5 =  findViewById(R.id.icon5);
        icon6 =  findViewById(R.id.icon6);
        icon7 =  findViewById(R.id.icon7);
        icon8 =  findViewById(R.id.icon8);

        icon1.setVisibility(View.GONE);
        icon2.setVisibility(View.GONE);
        icon3.setVisibility(View.GONE);
        icon4.setVisibility(View.GONE);
        icon5.setVisibility(View.GONE);
        icon6.setVisibility(View.GONE);
        icon7.setVisibility(View.GONE);
        icon8.setVisibility(View.GONE);

        confirm =  findViewById(R.id.confirm);
        edt_reason =  findViewById(R.id.edt_reason);
        edt_reason.setVisibility(View.GONE);


        setListners();
    }

    public void setListners() {
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        rl8.setOnClickListener(this);
        back.setOnClickListener(this);
        icon1.setOnClickListener(this);
        icon2.setOnClickListener(this);
        icon3.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl1:
                reason = getResources().getString(R.string.driver_cancel_reason1);
                switchViews("rl1");
                break;
            case R.id.rl2:
                reason = getResources().getString(R.string.driver_cancel_reason2);
                switchViews("rl2");
                break;
            case R.id.rl3:
                reason = getResources().getString(R.string.driver_cancel_reason3);
                switchViews("rl3");
                break;
            case R.id.rl4:
                reason = getResources().getString(R.string.driver_cancel_reason4);
                switchViews("rl4");
                break;
            case R.id.rl5:
                reason = getResources().getString(R.string.driver_cancel_reason5);
                switchViews("rl5");
                break;
            case R.id.rl6:
                reason = getResources().getString(R.string.driver_cancel_reason6);
                switchViews("rl6");
                break;
            case R.id.rl7:
                reason = getResources().getString(R.string.driver_cancel_reason7);
                switchViews("rl7");
                break;
            case R.id.rl8:
                reason = edt_reason.getText().toString();
                switchViews("rl8");
                break;
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                if (reason.isEmpty()){

                    Toast.makeText(this, "Select your reason", Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent = new Intent();
                    intent.putExtra("reason" , reason);
                    setResult(RESULT_OK, intent);
                    finish();

                }

                break;
        }
    }

    public void switchViews(String clicked) {
        switch (clicked) {
            case "rl1":
                icon1.setVisibility(View.VISIBLE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;
            case "rl2":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.VISIBLE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;
            case "rl3":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.VISIBLE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;

            case "rl4":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.VISIBLE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;

            case "rl5":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.VISIBLE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;

            case "rl6":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.VISIBLE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;

            case "rl7":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.VISIBLE);
                icon8.setVisibility(View.GONE);
                edt_reason.setVisibility(View.GONE);
                break;

            case "rl8":
                icon1.setVisibility(View.GONE);
                icon2.setVisibility(View.GONE);
                icon3.setVisibility(View.GONE);
                icon4.setVisibility(View.GONE);
                icon5.setVisibility(View.GONE);
                icon6.setVisibility(View.GONE);
                icon7.setVisibility(View.GONE);
                icon8.setVisibility(View.VISIBLE);
                edt_reason.setVisibility(View.VISIBLE);
                break;
        }
    }




}
