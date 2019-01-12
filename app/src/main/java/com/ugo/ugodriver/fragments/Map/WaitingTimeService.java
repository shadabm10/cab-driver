package com.ugo.ugodriver.fragments.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ugo.ugodriver.Base.BaseActivity;

/**
 * Created by ANDROID on 9/21/2016.
 */
public class WaitingTimeService extends Service {

    int i=0;
    Thread thread;
    BaseActivity ba;
    Context c;
    private boolean running;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ba = new BaseActivity();
        c = this;
        if((ba.getSharedPrefInt(this, ba.CONTINUE_TIME))!=0){
            Log.d("pref1", "hey "+ba.getSharedPrefInt(this, ba.CONTINUE_TIME));
            i = ba.getSharedPrefInt(this, ba.CONTINUE_TIME);
        }else{
            Log.d("pref2", "hey "+ba.getSharedPrefInt(this, ba.CONTINUE_TIME));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.

        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        running = true;

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(running) {

                        sleep(1000);
                        i = i+1;

                        long hours = i / 3600;
                        long minutes = (i % 3600) / 60;
                        long seconds = i % 60;

                        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);



                        Log.d("check", "hey"+i);
                        Intent intent = new Intent("intentKey");
                        intent.putExtra("value",timeString+"");
                        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                        ba.setSharedPrefInt(c, ba.CONTINUE_TIME, i);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}
