package com.ugo.ugodriver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rampo.updatechecker.UpdateChecker;
import com.squareup.picasso.Picasso;
import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.fragments.HelpFragment.HelpFrag;
import com.ugo.ugodriver.fragments.Map.Mapscreen;
import com.ugo.ugodriver.fragments.booking_history.HistoryScreen;
import com.ugo.ugodriver.fragments.myEarning.MyEarning;
import com.ugo.ugodriver.profile.ProfileActivity;

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
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    DrawerLayout drawer;
    LinearLayout rl_main;
    Fragment fragment;
    FragmentTransaction ft;
    TextView driver_name;
    Switch switch_on_off;
    CircleImageView driver_img;
    TextView tv_toolbar;
    ImageView img_arrow;
    RatingBar rating_driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         rl_main = findViewById(R.id.rl_main);
         drawer = findViewById(R.id.drawer_layout);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        img_arrow = findViewById(R.id.img_arrow);
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        setupDrawerToggle1();
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View header=navigationView.getHeaderView(0);
        driver_name=header.findViewById(R.id.driver_name);
        rating_driver=header.findViewById(R.id.rating_driver);
        driver_img = header.findViewById(R.id.imageView);
        switch_on_off=toolbar.findViewById(R.id.switch_on_off);

//        switch_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)()
//            }
//        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        fragment = new Mapscreen();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        //ft.addToBackStack(null);
        ft.commit();

        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new Mapscreen();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, fragment);
                //ft.addToBackStack(null);
                ft.commit();
                toggle.setDrawerIndicatorEnabled(true);
                img_arrow.setVisibility(View.GONE);
                tv_toolbar.setVisibility(View.GONE);
                switch_on_off.setVisibility(View.VISIBLE);
            }
        });

        UpdateChecker checker = new UpdateChecker(this);
        checker.start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

           super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            fragment = new Mapscreen();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            switch_on_off.setVisibility(View.VISIBLE);
            //ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.history) {
            fragment = new HistoryScreen();
            toggle.setDrawerIndicatorEnabled(false);
            img_arrow.setVisibility(View.VISIBLE);
            tv_toolbar.setVisibility(View.VISIBLE);
            switch_on_off.setVisibility(View.GONE);
            tv_toolbar.setText("History");
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            //ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.earning) {
            fragment = new MyEarning();
            tv_toolbar.setText("My Earning");
            toggle.setDrawerIndicatorEnabled(false);
            img_arrow.setVisibility(View.VISIBLE);
            tv_toolbar.setVisibility(View.VISIBLE);
            switch_on_off.setVisibility(View.GONE);

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            //ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.help) {
            fragment = new HelpFrag();
            toggle.setDrawerIndicatorEnabled(false);
            img_arrow.setVisibility(View.VISIBLE);
            tv_toolbar.setVisibility(View.VISIBLE);
            switch_on_off.setVisibility(View.GONE);
            tv_toolbar.setText("Help");
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            //ft.addToBackStack(null);
            ft.commit();

        }
        else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Logout");
            builder.setMessage("Do you want to logout");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do do my action here

                    logoutApi();

                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // I do not need any action here you might
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
          /*  removePreference(MainActivity.this);
            startActivity(new Intent(MainActivity.this , SplashScreen.class));

            finish();*/
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupDrawerToggle1() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                setData();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                rl_main.setTranslationX(slideOffset * drawerView.getWidth());
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };
        drawer.addDrawerListener(toggle);
    }

    public void setData(){
        driver_name.setText(getSharedPref(this,DRIVER_NAME));
        rating_driver.setRating(getSharedPrefFloat(this,DRIVER_RATING));
        if(!getSharedPref(this,DRIVER_IMAGE).isEmpty()){
            Picasso.with(this)
                    .load(getSharedPref(this,DRIVER_IMAGE))
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(this)
                    .into(driver_img);
        }
    }


    @Override
    protected void onResume() {
        setData();
        super.onResume();
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
    public void logoutApi(){

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Checking...");
        pDialog.show();


        String url = "http://u-go.in/api/driver_logout";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        params.put("id", getSharedPref(this, DRIVER_ID));

        Log.d("TAG" , "driver_logout - " + url);
        Log.d("TAG" , "driver_logout - " + params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        client.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TAG", "driver_logout- " + response.toString());

                if (response != null) {
                    try {

                        int status = response.optInt("status");
                        String message = response.optString("message");

                        if (status == 1){

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            removePreference(MainActivity.this);
                            startActivity(new Intent(MainActivity.this , SplashScreen.class));

                            finish();

                        }else {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
