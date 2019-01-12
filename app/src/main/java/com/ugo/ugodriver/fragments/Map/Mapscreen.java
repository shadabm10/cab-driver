package com.ugo.ugodriver.fragments.Map;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.cancel.CancelApi;
import com.ugo.ugodriver.cancel.CancelModel;
import com.ugo.ugodriver.cancel.CancellationReason;
import com.ugo.ugodriver.MapTrackingFragment;
import com.ugo.ugodriver.fragments.LocationAddress;
import com.ugo.ugodriver.otp.EnterOtp;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.rating.RateActivity;
import com.ugo.ugodriver.Rest.ApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.security.ProviderInstaller;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rampo.updatechecker.UpdateChecker;
import com.rampo.updatechecker.notice.Notice;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
/**
 * Created by jawed on 12/01/2018**
 */

public class Mapscreen extends MapTrackingFragment implements View.OnClickListener{


    //Things to do
    //Service run
    //update current latlng every 4 sec
    //switch view accordingly to the above api response

    BaseActivity ba;
    BookingModel bookingModel;
    Handler h = new Handler();
    int delay = 4000; //4 seconds
    Runnable runnable;
    SupportMapFragment mMapView;
    GoogleMap googleMap;
    Double lat,lng;
    Dialog newBookingDialog;
    Button btn_drop_off,btn_client_located,btn_reached,btn_cancel_trip;
    LinearLayout ll_call,ll_timer,ll_navigation;
    RelativeLayout rl_accepted_job,rl_my_loc, rl_navigation2;
    TextView dummy_booking,txt_current_loc, tv_show_pickup_add, rider_name, tv_distance, tv_plan;
    CardView card_header_main;
    private boolean isSoundPlaying = false;
    MediaPlayer mp;
    Toolbar toolbar;
    Switch switch_on_off;
    public static final int CANCEL_REQUEST=111;
    public static final int ENTER_OTP=222;
    public static final int RATE=333;
    boolean show_current_loc = false;
    CircleImageView rider_image;
    protected static final int REQUEST_PHONE_CALL = 98;
    String Availablity;
    ProgressDialog progressDialog;
    TextView tv_waiting_time;
    boolean click_accept_reject = false;
    String payment_mode;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GAIN_D", "onCreate: ");
        if (mMapView == null) {
            mMapView = SupportMapFragment.newInstance();
        }
        ba = new BaseActivity();

        updateAndroidSecurityProvider(getActivity());

        UpdateChecker checker = new UpdateChecker(getActivity());
        checker.setNotice(Notice.NOTIFICATION);
        checker.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapscreen, container, false);
        setViews(view);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // R.id.map is a layout
        transaction.replace(R.id.map, mMapView).commit();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // needed to get the map to display immediately


        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                Log.d("GainDriver", "onMapReady: ");
                googleMap = mMap;
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                setupMap(googleMap);


            }
        });


    /*    double lat = Double.parseDouble(bookingModel.getPick_lat());
        double lng = Double.parseDouble(bookingModel.getPick_lng());
        LatLng rider_pick_ll = new LatLng(lat, lng); ////your lat lng
        googleMap.addMarker(new MarkerOptions().position(rider_pick_ll).title("Pickup Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rider_pick_ll));
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));*/

  return  view;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        requestLocation();



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lng = location.getLongitude();
        lat = location.getLatitude();

        if (getActivity() != null) {
            /*LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(lng, lat,
                    getActivity(), new GeocoderHandler());*/

            getLocation(lat, lng);
        }


        if (!show_current_loc) {
            CameraPosition camPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)).zoom(17f).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camPosition));
            show_current_loc = true;
        }
    }


    private void showGPSDisabledAlertToUser() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setTitle("Permission");
        builder.setMessage("To Use Location services turn on your GPS");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                getActivity().finish();
                System.exit(0);
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("abc", "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Checking the request code of our request
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                //Displaying a toast
                //Toast.makeText(getActivity(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                //Toast.makeText(getActivity(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUEST_PHONE_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bookingModel.getCustomer_phone()));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }else {
                    startActivity(intent);
                }
            }
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reached:
                startServiceClass();
                bookingAction("reached", bookingModel.getBooking_id());
                click_accept_reject = false;
                break;
            case R.id.btn_client_located:
                startActivityForResult(new Intent(getActivity(), EnterOtp.class),ENTER_OTP);
                break;
            case R.id.btn_cancel_trip:
                stopServiceClass();
                startActivityForResult(new Intent(getActivity(), CancellationReason.class),CANCEL_REQUEST);
                break;
            case R.id.btn_drop_off:
                endTrip(bookingModel.getBooking_id());
                break;
            case R.id.dummy_booking:
                //newBooking();
                break;
            case R.id.ll_call:
                //call
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +bookingModel.getCustomer_phone()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(intent);
                    }
                } else {
                    startActivity(intent);
                }
                break;
            case R.id.ll_navigation:
                //navigate
                Intent inten = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ lat + "," + lng + "&daddr=" + bookingModel.getPick_lat() + "," + bookingModel.getPick_lng()));
                inten.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(inten);
                break;

            case R.id.rl_navigation2:
                //navigate
                Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ lat + "," + lng + "&daddr=" + bookingModel.getDrop_lat() + "," + bookingModel.getDrop_lng()));
                intent1.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivity(intent1);
                break;


        }

    }


    public void setViews(View view){

        progressDialog=new ProgressDialog(getActivity());
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        toolbar=getActivity().findViewById(R.id.toolbar);
        switch_on_off=toolbar.findViewById(R.id.switch_on_off);
        switch_on_off.setEnabled(true);
        Log.d("GC", "load1 "+ba.getSharedPref(getActivity(), ba.AVAILABILITY));
        if( ba.getSharedPref(getActivity(), ba.AVAILABILITY).equals("Online")) {
            switch_on_off.setText("Online");
            switch_on_off.setChecked(true);

        }else{
            switch_on_off.setText("Offline");
            switch_on_off.setChecked(false);

        }
        switch_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switch_on_off.isChecked()){
                    switch_on_off.setText("Online");
                    ba.setSharedPref(getActivity(), ba.AVAILABILITY, "Online");
                    Log.d("GC", "load "+ba.getSharedPref(getActivity(), ba.AVAILABILITY));
                }else{
                    switch_on_off.setText("Offline");
                    ba.setSharedPref(getActivity(), ba.AVAILABILITY, "Offline");
                    Log.d("GC", "load "+ba.getSharedPref(getActivity(), ba.AVAILABILITY));
                }
            }
        });

        dummy_booking= view.findViewById(R.id.dummy_booking);
        txt_current_loc= view.findViewById(R.id.txt_current_loc);
        btn_drop_off=view.findViewById(R.id.btn_drop_off);
        btn_client_located=view.findViewById(R.id.btn_client_located);
        btn_reached=view.findViewById(R.id.btn_reached);
        btn_cancel_trip=view.findViewById(R.id.btn_cancel_trip);
        //
        tv_show_pickup_add = view.findViewById(R.id.tv_show_pickup_add);
        rider_image = view.findViewById(R.id.rider_image);
        rider_name= view.findViewById(R.id.rider_name);
        tv_distance= view.findViewById(R.id.tv_distance);

        tv_plan = view.findViewById(R.id.tv_plan);


        ll_call= view.findViewById(R.id.ll_call);
        ll_timer= view.findViewById(R.id.ll_timer);
        ll_navigation= view.findViewById(R.id.ll_navigation);
        rl_accepted_job= view.findViewById(R.id.rl_accepted_job);
        rl_my_loc= view.findViewById(R.id.rl_my_loc);
        card_header_main= view.findViewById(R.id.card_header_main);
        tv_waiting_time = view.findViewById(R.id.tv_waiting_time);

        rl_navigation2 = view.findViewById(R.id.rl_navigation2);
        rl_navigation2.setVisibility(View.GONE);

        newBookingDialog = new Dialog(getActivity());
        newBookingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newBookingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newBookingDialog.setContentView(R.layout.newbooking);
        newBookingDialog.setCancelable(true);
        newBookingDialog.setCanceledOnTouchOutside(false);

        setListners();

    }

    private void setListners(){
        dummy_booking.setOnClickListener(this);
        btn_drop_off.setOnClickListener(this);
        btn_client_located.setOnClickListener(this);
        btn_reached.setOnClickListener(this);
        btn_cancel_trip.setOnClickListener(this);
        ll_call.setOnClickListener(this);
        ll_timer.setOnClickListener(this);
        ll_navigation.setOnClickListener(this);
        rl_navigation2.setOnClickListener(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("intentKey"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("value");
            tv_waiting_time.setText(message);

        }
    };

    // Method to start the service
    public void startServiceClass() {
        getContext().startService(new Intent(getActivity(), WaitingTimeService.class));
    }

    public void stopServiceClass() {
        tv_waiting_time.setText("00:00:00");
        getContext().stopService(new Intent(getActivity(), WaitingTimeService.class));
        ba.setSharedPrefInt(getActivity(), ba.CONTINUE_TIME, 0);
    }



    @Override
    public void onPause() {

        h.removeCallbacks(runnable);
        super.onPause();

    }

    @Override
    public void onResume() {
        h.postDelayed(new Runnable() {
            public void run() {
                //do something

                if(lat!=null) {
                    updateLatlng();
                }
                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();

    }


    private void newBookingSound(){
        AudioManager audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        Log.d("sound", "notifSound: ");
        try{
            if(!isSoundPlaying){
                isSoundPlaying = true;
                Log.d("sound", "notifSound: not pla");

                mp = MediaPlayer.create(getActivity(), R.raw.booking);
                if(!mp.isPlaying()) {
                    Log.d("sound", "notifSound: start ");
                    mp.start();
                }
                isSoundPlaying = true;


                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp1) {
                        Log.d("GainDriver", "onCompletion: ");
                        mp1.stop();
                        isSoundPlaying=false;

                    }
                });
            }


        }catch (Exception e){

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK) {
            Log.d("SSSSS" , "request code "+requestCode);
            switch (requestCode) {
                case CANCEL_REQUEST:
                    cancelBooking(data.getStringExtra("reason"), bookingModel.getBooking_id());
                    break;
                case ENTER_OTP:
                    startTrip(data.getStringExtra("otp"), tv_waiting_time.getText().toString() , bookingModel.getBooking_id());
                    break;
                case RATE:
                    Log.d("SSSSS" , "Dropped off");
                    Log.d("SSSSS" , ""+data.getStringExtra("drop"));
                    rl_accepted_job.setVisibility(View.GONE);
                    hideUnhide("Drop");
                    break;
            }
        }
    }


    public void hideUnhide(String clicked){
        switch (clicked){
            case "Accept":
                btn_drop_off.setVisibility(View.GONE);
                switch_on_off.setVisibility(View.GONE);
                card_header_main.setVisibility(View.VISIBLE);
                rl_accepted_job.setVisibility(View.VISIBLE);
                btn_reached.setVisibility(View.VISIBLE);
                btn_cancel_trip.setVisibility(View.VISIBLE);
                btn_client_located.setVisibility(View.GONE);
                ll_call.setVisibility(View.VISIBLE);
                ll_navigation.setVisibility(View.VISIBLE);
                ll_timer.setVisibility(View.GONE);
                rl_navigation2.setVisibility(View.GONE);
                tv_show_pickup_add.setText(bookingModel.getPick_address());
                if(getActivity()!=null) {
                    Picasso.with(getActivity())
                            .load(bookingModel.getCustomer_image())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                            .centerInside()
                            .tag(getActivity())
                            .into(rider_image);
                }
                rider_name.setText(bookingModel.getCustomer_name());
                tv_distance.setText(bookingModel.getBooking_km() + " KM");
                break;
            case "Reached":
                btn_drop_off.setVisibility(View.GONE);
                switch_on_off.setVisibility(View.GONE);
                card_header_main.setVisibility(View.VISIBLE);
                rl_accepted_job.setVisibility(View.VISIBLE);
                btn_reached.setVisibility(View.GONE);
                btn_client_located.setVisibility(View.VISIBLE);
                btn_cancel_trip.setVisibility(View.VISIBLE);
                ll_call.setVisibility(View.VISIBLE);
                ll_timer.setVisibility(View.VISIBLE);
                ll_navigation.setVisibility(View.GONE);
                rl_navigation2.setVisibility(View.GONE);
                tv_show_pickup_add.setText(bookingModel.getPick_address());
                Picasso.with(getActivity())
                        .load(bookingModel.getCustomer_image())
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_user)
                        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                        .centerInside()
                        .tag(getActivity())
                        .into(rider_image);
                rider_name.setText(bookingModel.getCustomer_name());
                tv_distance.setText(bookingModel.getBooking_km() + " KM");
                break;
            case "Located":
                card_header_main.setVisibility(View.VISIBLE);
                switch_on_off.setVisibility(View.GONE);
                btn_drop_off.setVisibility(View.VISIBLE);
                rl_navigation2.setVisibility(View.VISIBLE);
                rl_accepted_job.setVisibility(View.VISIBLE);
                btn_reached.setVisibility(View.GONE);
                btn_client_located.setVisibility(View.GONE);
                btn_cancel_trip.setVisibility(View.GONE);
                ll_call.setVisibility(View.GONE);
                ll_timer.setVisibility(View.GONE);
                ll_navigation.setVisibility(View.GONE);
                //--
                tv_show_pickup_add.setText(bookingModel.getPick_address());
                Picasso.with(getActivity())
                        .load(bookingModel.getCustomer_image())
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_user)
                        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                        .centerInside()
                        .tag(getActivity())
                        .into(rider_image);
                rider_name.setText(bookingModel.getCustomer_name());
                tv_distance.setText(bookingModel.getBooking_km() + " KM");
                break;
            case "Drop":
                card_header_main.setVisibility(View.GONE);
                btn_drop_off.setVisibility(View.GONE);
                rl_accepted_job.setVisibility(View.GONE);
                rl_my_loc.setVisibility(View.VISIBLE);
                switch_on_off.setVisibility(View.VISIBLE);
                rl_navigation2.setVisibility(View.GONE);
                break;
            case "Cancel":
                card_header_main.setVisibility(View.GONE);
                btn_drop_off.setVisibility(View.GONE);
                rl_accepted_job.setVisibility(View.GONE);
                rl_my_loc.setVisibility(View.VISIBLE);
                switch_on_off.setVisibility(View.VISIBLE);
                rl_navigation2.setVisibility(View.GONE);
                break;
        }
    }

    public void newBooking(){
        if(!newBookingDialog.isShowing()) {
            Log.d("GC" , "open dialog");
            newBookingDialog.show();
            stopServiceClass();
            newBookingSound();
            MapView mMapView = newBookingDialog.findViewById(R.id.mapView);
            MapsInitializer.initialize(getActivity());
            mMapView.onCreate(null);
            mMapView.onResume();// needed to get the map to display immediately
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    double lat = Double.parseDouble(bookingModel.getPick_lat());
                    double lng = Double.parseDouble(bookingModel.getPick_lng());
                    LatLng rider_pick_ll = new LatLng(lat, lng); ////your lat lng
                    googleMap.addMarker(new MarkerOptions().position(rider_pick_ll).title("Pickup Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(rider_pick_ll));
                    googleMap.getUiSettings().setZoomControlsEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                }
            });
            CircleImageView rider_img = newBookingDialog.findViewById(R.id.rider_image);
            TextView tv_ride_type =  newBookingDialog.findViewById(R.id.tv_ride_type);
            TextView rider_name =  newBookingDialog.findViewById(R.id.rider_name);
            TextView accept =  newBookingDialog.findViewById(R.id.accept);
            TextView reject =  newBookingDialog.findViewById(R.id.reject);
            TextView pick_add =  newBookingDialog.findViewById(R.id.pick_add);
            Picasso.with(getActivity())
                    .load(bookingModel.getCustomer_image())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(getActivity())
                    .into(rider_img);

            if (bookingModel.getBooking_type().matches("city_taxi")){
                tv_ride_type.setText("City Taxi");
            }else if (bookingModel.getBooking_type().matches("outstation")){
                tv_ride_type.setText("Outstation");
            }else if (bookingModel.getBooking_type().matches("rental")){
                tv_ride_type.setText("Rental");
            }

            rider_name.setText(bookingModel.getCustomer_name());
            pick_add.setText(bookingModel.getPick_address());
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click_accept_reject = true;

                    if (mp != null){
                        if (mp.isPlaying()) {
                            mp.stop();
                            isSoundPlaying = false;
                        }
                    }

                    newBookingDialog.dismiss();
                    bookingAction("accepted", bookingModel.getBooking_id());
                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click_accept_reject = true;

                    if (mp != null){
                        if (mp.isPlaying()) {
                            mp.stop();
                            isSoundPlaying = false;
                        }
                    }

                    newBookingDialog.dismiss();
                    bookingAction("rejected", bookingModel.getBooking_id());
                }
            });
        }
    }

    public void updateLatlng(){
      //  Toast.makeText(getActivity(),"Update LAtlong",Toast.LENGTH_SHORT).show();
        if(getActivity()!=null && ba.getSharedPref(getActivity(),ba.DRIVER_ID)!=null) {
            Availablity = "0";
            if (switch_on_off.isChecked()) {
                Availablity = "1";
            }
            MapApi apiService = ApiClient.getClient().create(MapApi.class);
            Log.d("TAG", "id: " + ba.getSharedPref(getActivity(), ba.DRIVER_ID) + "");
            Log.d("TAG", "lat: " + lat + " " + lng);
            Log.d("TAG", "Availability: " + Availablity);
            Call<Map> call = apiService.update(
                    ba.getSharedPref(getActivity(), ba.DRIVER_ID),
                    lat.toString(),
                    lng.toString(),
                    Availablity
            );
            call.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {

                    try{
                        if (response != null) {
                            Log.d("TAG", "Raw1: "+response);

                            tv_plan.setText("Your Plan : "+response.body().getPlan_details().getPlan_name());
                           // Toast.makeText(getActivity(),"Response =" +response.body().getStatus(),Toast.LENGTH_SHORT).show();

                            if (response.body().getStatus() == 1 ) {

                                Log.d("TAG", "booking info : " + response.body().getBooking_info().getBooking_id());
                                if (!response.body().getBooking_info().getDriver_id()
                                        .equals(ba.getSharedPref(getActivity(), ba.DRIVER_ID))){
                                    click_accept_reject = false;
                                }

                                if (response.body().getBooking_info() != null
                                        && (response.body().getBooking_info().getBooking_status().equals("pending")
                                         || response.body().getBooking_info().getBooking_status().equals("rejected"))
                                        && (!isDriverExist(response.body().getBooking_info().getRejected_by_driver(),
                                                ba.getSharedPref(getActivity(), ba.DRIVER_ID)))
                                        && Availablity.equals("1")) {

                                    Log.d("TAG", "click_accept_reject: "+click_accept_reject);


                                    if (!click_accept_reject){

                                        if(!newBookingDialog.isShowing()) {
                                            bookingModel = response.body().getBooking_info();
                                            newBooking();
                                            Log.d("TAG", "booking info : " + response.body().getBooking_info().getBooking_id());
                                        }
                                    }

                                }else if(response.body().getBooking_info() != null
                                        && response.body().getBooking_info().getBooking_status().equals("accepted")){
                                    bookingModel = response.body().getBooking_info();
                                    hideUnhide("Accept");
                                }else if(response.body().getBooking_info() != null
                                        && response.body().getBooking_info().getBooking_status().equals("reached")){
                                    bookingModel = response.body().getBooking_info();
                                    hideUnhide("Reached" );
                                }else if(response.body().getBooking_info() != null
                                        && response.body().getBooking_info().getBooking_status().equals("assigned")){
                                    bookingModel = response.body().getBooking_info();
                                    hideUnhide("Located" );
                                }else{
                                    hideUnhide("Cancel");
                                }


                                payment_mode = response.body().getBooking_info().getPayment_mode();

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Log.d("LOG", "onResponse: " + t.getMessage());
                    //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void bookingAction(final String action, String booking_id){
        MapApi apiService = ApiClient.getClient().create(MapApi.class);
        Call<Map> call = apiService.booking(
                ba.getSharedPref(getActivity(),ba.DRIVER_ID),
                booking_id,
                action
        );
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));
        Log.d("TAG", "bookingid: "+booking_id);
        Log.d("TAG", "action: "+action);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map>call, Response<Map> response) {

                try{

                    if(response != null ){
                        Log.d("TAG", "status: "+response.body().getStatus());
                        Log.d("TAG", "msg: "+response.body().getMessage());

                        if(action.equals("accepted")){

                            hideUnhide("Accept");
                        }
                        else if(action.equals("rejected")){

                        }else if(action.equals("reached")){

                            hideUnhide("Reached" );
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("LOG", "onResponse: "+t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelBooking(String reason, String booking_id){
        progressDialog.setMessage("Cancelling ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        CancelApi apiService = ApiClient.getClient().create(CancelApi.class);
        Call<CancelModel> call = apiService.cancelBooking(
                ba.getSharedPref(getActivity(),ba.DRIVER_ID),
                booking_id,
                "cancelled",
                reason
        );
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));
        Log.d("TAG", "bookingid: "+booking_id);
        Log.d("TAG", "action: "+reason);
        call.enqueue(new Callback<CancelModel>() {
            @Override
            public void onResponse(Call<CancelModel>call, Response<CancelModel> response) {

                try{

                    if(response != null){
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText( getActivity() ,"Cancelled", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "status: "+response.body().getStatus());
                        Log.d("TAG", "msg: "+response.body().getMessage());
                        hideUnhide("Cancel");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
            @Override
            public void onFailure(Call<CancelModel> call, Throwable t) {
                Log.d("LOG", "onResponse: "+t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //enter OTP
    private void startTrip(String otp , String waiting_time , String booking_id){
        progressDialog.setMessage("Starting Trip ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        MapApi apiService = ApiClient.getClient().create(MapApi.class);
        Call<Map> call = apiService.startTrip(
                ba.getSharedPref(getActivity(), ba.DRIVER_ID),
                booking_id,
                otp,
                waiting_time
        );
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));
        Log.d("TAG", "bookingid: "+booking_id);
        Log.d("TAG", "otp: "+otp);
        Log.d("TAG", "waiting_time: "+waiting_time);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map>call, Response<Map> response) {

                try{

                    if(response != null){
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        if(response.body().getStatus()==1){
                            Toast.makeText( getActivity() ,"Started", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "status: "+response.body().getStatus());
                            Log.d("TAG", "msg: "+response.body().getMessage());
                            hideUnhide("Located");
                        }else{
                            //if wrong otp
                            Toast.makeText( getActivity() ,"Wrong OTP, Enter again", Toast.LENGTH_LONG).show();
                            startActivityForResult(new Intent(getActivity(), EnterOtp.class),ENTER_OTP);
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("LOG", "onResponse: "+t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //dropOff
    private void endTrip(String booking_id) {
        progressDialog.setMessage("Stopping Trip ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        MapApi apiService = ApiClient.getClient().create(MapApi.class);
        Call<Map> call = apiService.endTrip(
                ba.getSharedPref(getActivity(), ba.DRIVER_ID),
                booking_id,
                lat,
                lng
        );
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));
        Log.d("TAG", "bookingid: "+booking_id);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map>call, Response<Map> response) {

                try {
                    if(response != null){
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText( getActivity() ,"Stopped", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "status: "+response.body().getStatus());
                        Log.d("TAG", "msg: "+response.body().getMessage());
                        rl_accepted_job.setVisibility(View.GONE);
                        rl_navigation2.setVisibility(View.GONE);


                        Intent intent1 = new Intent(getActivity(), RateActivity.class);
                        intent1.putExtra("lat" , lat+"");
                        intent1.putExtra("lng" , lng+"");
                        intent1.putExtra("data" , response.body().getFare_Details());
                        intent1.putExtra("payment_mode" , payment_mode);
                        startActivityForResult(intent1, RATE);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("LOG", "onResponse: "+t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }



    ///////////////////////////

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

    public void getLocation(final double lat, final double lng){

        String URL ="https://maps.googleapis.com/maps/api/geocode/json?latlng="
                +lat+","+lng+"&key="
                +getResources().getString(R.string.geocoding_api_key);

        Log.d(TAG, "URL = "+URL);

        AsyncHttpClient client = new AsyncHttpClient();

        client.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.get(URL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //Here response will be received in form of JSONObject

              //  Log.d(TAG,"Server response - "+response );

                try {

                    JSONArray results = response.getJSONArray("results");

                    JSONObject obj = results.getJSONObject(0);

                    String f_address = obj.getString("formatted_address");
                    Log.d(TAG, "f_address: "+f_address);

                    txt_current_loc.setText(f_address);


                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(getActivity());
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    break;
                default:
                    locationAddress = null;
            }

            if (locationAddress != null){

                txt_current_loc.setText(locationAddress);
            }

        }
    }


    private boolean isDriverExist(String s, String id){
        boolean isE = false;

        if (s != null){
            try {

                String[] array = s.split(",");

                for (int i = 0; i < array.length; i++){
                    Log.d("LOG", "array: "+array[i]);
                    if (id.equals(array[i])){
                        isE = true;
                        break;
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        Log.d("LOG", "isE: "+isE);

        return isE;
    }

}

