package com.ugo.ugodriver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public abstract class MapTrackingFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    protected LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    protected GoogleMap mGoogleMap;
    protected static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    protected synchronized void buildGoogleApiClient() {

        Log.d("jwd", "buildGoogleApiClient: ");


        int status = getActivity().getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                getActivity().getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }


    protected void setupMap(GoogleMap googleMap){
        Log.d("jwd", "setupMap: ");
        mGoogleMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        else {
            mGoogleMap.setMyLocationEnabled(true);
            buildGoogleApiClient();
        }
    }



    protected void requestLocation(){
        Log.d("jwd", "requestLocation: ");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(1000);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected String setAddress(double latitude, double longitude, TextView textView){
        Log.d("jwd", "setaddress: ");
        String str_result = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try{
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if(textView != null){
                textView.setText(addresses.get(0).getAddressLine(0));
                str_result = addresses.get(0).getAddressLine(0);
            }

        }catch (Exception e){
            Log.d(TAG+"error",e.toString());
        }

        return str_result;
    }


    public  void getLocation(final double lat, final double lng){

        String URL ="https://maps.googleapis.com/maps/api/geocode/json?latlng="
                +lat+","+lng+"&key="
                +getResources().getString(R.string.google_api_key);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //Here response will be received in form of JSONObject

                Log.d(TAG,"Server response - "+response );

                try {

                    JSONArray results = response.getJSONArray("results");

                    for(int i = 0; i<results.length(); i++){
                        JSONObject obj = results.getJSONObject(0);

                        String f_address = obj.getString("formatted_address");
                        Log.d(TAG, "f_address: "+f_address);

                        break;
                    }

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

}

