package com.ugo.ugodriver.fragments.booking_history;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.fragments.Map.BookingModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * Created by ANDROID on 2/1/2018.
 */

public class HistoryDetails extends BaseActivity  implements View.OnClickListener, OnMapReadyCallback{

    TextView rider_name, tv_distance, tv_dist, tv_time, tv_fare;
    BookingModel booking ;
    CircleImageView rider_image;
    ImageView img_back;
    RatingBar ratingbar;
    EditText edt_comment;
    TextView btn_rate;
    RelativeLayout rl_coupon, rl_guider,rl_registration;
    TextView tv_ride_fare, tv_coupon_amt, tv_tax_amt, tv_gide_fee;

    //MapView mMapView;
    SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_history_details);
        setViews();


    }

    public void setViews(){

        progressDialog=new ProgressDialog(this);

        booking = (BookingModel)getIntent().getSerializableExtra("booking");
        rider_name = findViewById(R.id.rider_name);
        tv_distance = findViewById(R.id.distance);
        rl_registration = findViewById(R.id.rl_registration);
        tv_dist= findViewById(R.id.dist);
        tv_time=findViewById(R.id.time);
        tv_fare=findViewById(R.id.fare);
        rider_image = findViewById(R.id.rider_image);
        img_back = findViewById(R.id.back);
        img_back.setOnClickListener(this);
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        btn_rate =  findViewById(R.id.btn_rate);
        btn_rate.setOnClickListener(this);


        rl_coupon = (RelativeLayout) findViewById(R.id.rl_coupon);
        rl_guider = (RelativeLayout) findViewById(R.id.rl_guider);
        tv_ride_fare = (TextView) findViewById(R.id.tv_ride_fare);
        tv_coupon_amt = (TextView) findViewById(R.id.tv_coupon_amt);
        tv_tax_amt = (TextView) findViewById(R.id.tv_tax_amt);
        tv_gide_fee = (TextView) findViewById(R.id.tv_gide_fee);


       /* mMapView = findViewById(R.id.mapView);
        MapsInitializer.initialize(this);
        mMapView.onCreate(null);
        mMapView.onResume();// needed to get the map to display immediately*/
        setValues();
    }

    public void setValues(){

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        rider_name.setText(booking.getCustomer_name());
        tv_distance.setText(booking.getBooking_km()+ " KM");
        tv_dist.setText(booking.getBooking_km()+ " KM");
        tv_time.setText(secondMinHour(Integer.parseInt(booking.getBooking_total_time())));

        Picasso.with(this)
                .load(booking.getCustomer_image())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(this)
                .into(rider_image);



        tv_ride_fare.setText("₹ "+booking.getTotal_fare());
        tv_fare.setText("₹ "+booking.getTotal_fare());

        float total_fare = 0;

        if (booking.getCoupon_applied().matches("Y")){
            tv_coupon_amt.setText(booking.getCoupon_amount());
            rl_coupon.setVisibility(View.VISIBLE);
            total_fare = Float.valueOf(booking.getTotal_fare()) -
                    Float.valueOf(booking.getCoupon_amount());
        }else {
            rl_coupon.setVisibility(View.GONE);
        }


        float tax_amt = total_fare * booking.getGst_rate();
        tv_tax_amt.setText("₹ "+df.format(tax_amt));

        total_fare = total_fare + tax_amt;

        if (booking.getGuide().matches("Y")){
            total_fare = total_fare + booking.getGuide_charges();
            tv_gide_fee.setText("₹ "+booking.getGuide_charges());
            rl_guider.setVisibility(View.VISIBLE);
        }else {
            rl_guider.setVisibility(View.GONE);
        }

      //  tv_fare.setText("₹ "+df.format(total_fare));



        if (booking.getCustomer_rate() == 0){
            rl_registration.setVisibility(View.GONE);
            edt_comment.setVisibility(View.GONE);

        }else {
            ratingbar.setRating(booking.getCustomer_rate());
            ratingbar.setOnRatingBarChangeListener(null);
            rl_registration.setVisibility(View.GONE);
            edt_comment.setVisibility(View.GONE);
        }


      /*  mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                double lat1 = Double.parseDouble(booking.getPick_lat());
                double lng1 = Double.parseDouble(booking.getPick_lng());
                
              *//*  LatLng rider_pick_ll = new LatLng(lat1, lng1); ////your lat lng
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rider_pick_ll, 12.0f));
                googleMap.addMarker(new MarkerOptions()
                        .position(rider_pick_ll)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_icon)));*//*

                
                
                double lat2 = Double.parseDouble(booking.getDrop_lat());
                double lng2 = Double.parseDouble(booking.getDrop_lng());
              *//*  LatLng rider_drop_ll = new LatLng(lat2, lng2);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rider_drop_ll, 12.0f));
                googleMap.addMarker(new MarkerOptions()
                        .position(rider_drop_ll)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_icon)));*//*

              Log.d("GC" , "val "+lat1+" "+ lng1+" "+ lat2+" "+ lng2);
                drawPolyline3(googleMap, lat1, lng1, lat2, lng2);
            }
        });*/
    }

    @Override
    public void onMapReady(GoogleMap googleMa) {
        googleMap = googleMa;
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);


        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //  double lat1 = 22.567438;
        //  double long1 = 88.375214;

        double lat1 = Double.parseDouble(booking.getPick_lat());
        double lng1 = Double.parseDouble(booking.getPick_lng());
        double lat2 = Double.parseDouble(booking.getDrop_lat());
        double lng2 = Double.parseDouble(booking.getDrop_lng());

        LatLng pick_latlong = new LatLng(lat1, lng1);

        LatLng drop_latlong = new LatLng(lat2, lng2);



        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(pick_latlong);
        markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.mipmap.red_pin_marker));
        googleMap.addMarker(markerOptions1);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pick_latlong));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(googleMa.getCameraPosition().target)
                .zoom(16)
                .bearing(30)
                .tilt(45)
                .build()));




        drawPolyline3(googleMap, pick_latlong.latitude, pick_latlong.longitude, drop_latlong.latitude, drop_latlong.longitude);
    }


    ////////////

    String requestUrl = null;
    private PolylineOptions polylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private List<LatLng> polyLineList;
    String TAG = "Polyline";
    public void drawPolyline3(final GoogleMap mMap,double lat11,double long11, double lat22, double long22){


        try {
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&"
                    + "transit_routing_preference=less_driving&"
                    + "origin=" + lat11 + "," + long11 + "&"
                    + "destination=" + lat22 + "," + long22 + "&"
                    + "key=" + getResources().getString(R.string.google_api_key);
            Log.d(TAG, requestUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response + "");

                            try {
                                JSONArray jsonArray = response.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);
                                    Log.d(TAG, polyLineList + "");
                                }

                                //Adjusting bounds

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList) {
                                    builder.include(latLng);
                                }
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 40);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(Color.BLACK);
                                polylineOptions.width(8);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyLine = mMap.addPolyline(polylineOptions);


                                MarkerOptions markerOptions2 = new MarkerOptions();
                                markerOptions2.position(polyLineList.get(polyLineList.size() - 1));
                                markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.mipmap.green_pin_marker));
                                mMap.addMarker(markerOptions2);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error + "");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }



    private String secondMinHour(int i){
        long hours = i / 3600;
        long minutes = (i % 3600) / 60;
        long seconds = i % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.btn_rate:
               // sendFeedback();
                break;
        }
    }


    /*public void sendFeedback(){

        progressDialog.setMessage("Sending ..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RateApi apiService = ApiClient.getClient().create(RateApi.class);
        Call<RateModel> call = apiService.sendFeedback(booking.getDriver_id()
                , booking.getBooking_id()
                , String.valueOf(ratingbar.getRating())
                , edt_comment.getText().toString()
        );

        call.enqueue(new Callback<RateModel>() {
            @Override
            public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                Log.d("TAG", "Raw1: ");
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<RateModel> call, Throwable t) {
                Log.d("LOG", "onResponse: " + t.getMessage());
                //Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }*/
}
