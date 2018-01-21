package com.example.yougourta.effortless;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.yougourta.effortless.utils.CallStatus;
import com.example.yougourta.effortless.utils.ProximitySensor;
import com.example.yougourta.effortless.utils.ScreenLock;
import com.example.yougourta.effortless.utils.Vibration;

public class ScreenLockerService extends Service {

    protected static final String TAG = ScreenLockerService.class.getCanonicalName();
    BroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        ProximitySensor.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);

        System.out.println("Screen is being locked");
        ScreenLock.lockScreen(getApplicationContext());
        Vibration.vibrate(getApplicationContext());

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Screen....");

                try {
                    ProximitySensor proximitySensor = ProximitySensor.getInstance(context);
                    if (proximitySensor.hasProximitySensorHardware() && proximitySensor.isClose()) {
                        Log.d(TAG, "lock");
                        if (CallStatus.isCallStateIdle(context)) {
                            ScreenLock.lockScreen(context);
                            Vibration.vibrate(context);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Exception occurred: " + e);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}