package com.rabidmongeese.CloudReminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class GeofenceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		
		if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {
			handleGeofenceError(context, intent);
		} else if (TextUtils.equals(action,  GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {
			handleGeofenceTransition(context, intent);
		} else {
			Log.e(GeofenceUtils.APPTAG, "Invalid action: "+ action);
		}
		
	}

	private void handleGeofenceTransition(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
	}

	private void handleGeofenceError(Context context, Intent intent) {
		String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
		Log.e(GeofenceUtils.APPTAG, msg);
		
	}

}
