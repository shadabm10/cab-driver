package com.ugo.ugodriver.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ugo.ugodriver.R;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by jawed on 15/8/17.
 */

public class Help extends Fragment {

    EditText edt_query;
    TextView sendquery,call;
    ProgressDialog progressDialog;
    Dialog sd;
    String countryname;
    ArrayList<HashMap<String,String>> array_list=new ArrayList<>();



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);



        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        if (progressDialog.isShowing())
            progressDialog.dismiss();




        return view;

    }



}
