package com.example.yougourta.effortless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.yougourta.effortless.utils.Vibration;

public class ScreenTurnedOnReceiver extends BroadcastReceiver {
	private static final String TAG = ScreenTurnedOnReceiver.class.getCanonicalName();


	@Override
	public void onReceive(Context context, Intent intent) {
		//WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//wifi.setWifiEnabled(true);
		Log.d(TAG, "ScreenTurnedOnService.onReceive");
		Vibration.vibrate(context);
	}
}
