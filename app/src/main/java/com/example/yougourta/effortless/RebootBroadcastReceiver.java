package com.example.yougourta.effortless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yougourta.effortless.utils.AppSharedPreferences;

/**
 * Will start the service automatically after device reboot.
 *
 */
public class RebootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean autoStart = AppSharedPreferences.isAutoStart(context);
        if (autoStart) {
            Intent serviceIntent = new Intent(context, ScreenLockerService.class);
            context.startService(serviceIntent);
        }
    }
}
