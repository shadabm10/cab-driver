package com.ugo.ugodriver;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by developer on 15/1/19.
 */

public class Global extends Application {

    private static Global mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized Global getInstance() {
        return mInstance;
    }


    public boolean isOnline=false;
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
