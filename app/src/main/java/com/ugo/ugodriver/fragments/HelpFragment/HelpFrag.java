package com.ugo.ugodriver.fragments.HelpFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ugo.ugodriver.Adapter.HelpAdapter;
import com.ugo.ugodriver.Adapter.HistoryAdapter;
import com.ugo.ugodriver.Base.BaseActivity;
import com.ugo.ugodriver.R;
import com.ugo.ugodriver.Rest.ApiClient;
import com.ugo.ugodriver.fragments.Map.BookingModel;
import com.ugo.ugodriver.fragments.booking_history.HistoryApi;
import com.ugo.ugodriver.fragments.booking_history.HistoryModel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jawed on 15/8/17.
 */

public class HelpFrag extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    BaseActivity ba;
    TextView tv_no_data;
    Toolbar toolbar;
    Switch switch_on_off;
    ProgressDialog progressDialog;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_history, container, false);
        setViews(view);
        driverGetsOffline();
        getHistory();


        return view;

    }

    public void setViews(View view) {
        ba = new BaseActivity();
        toolbar = getActivity().findViewById(R.id.toolbar);
        switch_on_off = toolbar.findViewById(R.id.switch_on_off);
        progressDialog=new ProgressDialog(getActivity());
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        recyclerView=view.findViewById(R.id.recyclervw);
        tv_no_data = view.findViewById(R.id.no_data);
        tv_no_data.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(getActivity());
    }

    private void driverGetsOffline(){
        switch_on_off.setText("Offline");
        switch_on_off.setEnabled(false);
        ba.setSharedPref(getActivity(), ba.AVAILABILITY, "Offline");
        Log.d("GC", "saved "+ba.getSharedPref(getActivity(), ba.AVAILABILITY));
    }

    private void getHistory(){
        progressDialog.setMessage("Fetching History ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        HistoryApi apiService = ApiClient.getClient().create(HistoryApi.class);
        Call<HistoryModel> call = apiService.getHistory(ba.getSharedPref(getActivity(),ba.DRIVER_ID));
        Log.d("TAG", "id: "+ba.getSharedPref(getActivity(),ba.DRIVER_ID));

        call.enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel>call, Response<HistoryModel> response) {
                progressDialog.dismiss();

                try{
                    if(response != null){
                        //Log.d("TAG", "status: "+response.body().getStatus());
                        //Log.d("TAG", "msg: "+response.body().getMessage());
                        if(response.body().getBooking_Details()!=null){
                            Log.d("TAG", "msg: "+response.body().getBooking_Details().size());
                            ArrayList<BookingModel> bookings = response.body().getBooking_Details();
                            populateRecyclerview(bookings);
                            tv_no_data.setVisibility(View.GONE);
                        }else{
                            tv_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("LOG", "onResponse: "+t.getMessage());
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void populateRecyclerview(ArrayList<BookingModel> bookings){
        HelpAdapter helpAdapter = new HelpAdapter(bookings);
        recyclerView.setAdapter(helpAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
