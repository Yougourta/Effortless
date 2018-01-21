package com.example.yougourta.effortless.utils;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;

import com.example.yougourta.effortless.ScreenLockerDeviceAdminReceiver;

public class ScreenLock {
	static final int RESULT_ENABLE = 1;
	private static final String TAG = ScreenLock.class.getCanonicalName();

	public static void lockScreen(Context context) {
		Log.d(TAG, "lockScreen");
		forceLock(context);
	}

	public static void unlockScreen(Context context) {
		Log.d(TAG, "lockScreen");
		unlockScreenNow(context);
	}

	// Actual Unlock and Turning on Wifi happens here
	private static void unlockScreenNow(Context context){
		Log.d("dialog", "unlocking screen now");
		ComponentName componentName = new ComponentName(context, ScreenLockerDeviceAdminReceiver.class);
		boolean active = ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).isAdminActive(componentName);
		if(active) {
			Log.d("dialog", "Inside the isActive Method");
			//WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			//This is to get control on the power manager i.e. Lock screen here
			PowerManager powermanager = ((PowerManager) context.getSystemService(Context.POWER_SERVICE));
			PowerManager.WakeLock wakeLock = powermanager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
					PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "tag");
			wakeLock.acquire(12000);

			// This is to unlock the screen(swipe/password/pattern/pin)
			KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("tag");
			keyguardLock.disableKeyguard();

			// This is to Enable the Wifi
			//wifi.setWifiEnabled(true);
		}
	}

	private static void forceLock(Context context) {
		ComponentName componentName = new ComponentName(context, ScreenLockerDeviceAdminReceiver.class);
		//WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		boolean active = ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).isAdminActive(componentName);
		if (active)
		{
			((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).lockNow();
			 //wifi.setWifiEnabled(false);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
			builder.setMessage("You can't lock my screen because you are not an active Admin!");
			builder.setPositiveButton("I admit defeat", null);
			builder.show();
			return;
		}
	}
}
