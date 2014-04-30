package com.rabidmongeese.CloudReminders;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.*;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	private GoogleMap map;
	private ReminderSQLiteHelper db;

	private LocationManager locationManager;
	private LocationListener locationListener;
	
	List<Reminder> reminders;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		db = new ReminderSQLiteHelper(this);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		reminders = db.getAllReminders();

		for (Reminder r : reminders) {
			Log.w("Cloud Reminders", r.getLatitude() + " " + r.getLongitude());
			LatLng pos = new LatLng(r.getLatitude() / Math.pow(10, 6),
					r.getLongitude() / Math.pow(10, 6));
			Marker m = map.addMarker(new MarkerOptions().position(pos).title(
					r.getText()));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
		
		Log.w("CloudReminders", "Part1");
		locationListener = new UserLocationListener();
		Log.w("CloudReminders", "Part 1");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				5000, 5, locationListener);
		Log.w("CloudReminders", "Part 1");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_map, container,
					false);
			return rootView;
		}
	}

	public class UserLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			for (Reminder r : reminders) {
				if (distance(location.getLatitude(), location.getLongitude(), r.getLatitude(), r.getLongitude()) <= .02) {
					Toast.makeText(getBaseContext(),
							r.getText(),
							Toast.LENGTH_LONG).show();
				}
			}
			Log.w("CloudReminders", location.getLatitude() + " : " + location.getLongitude());

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}
		
		private double distance(double lat1, double lon1, double lat2, double lon2) {
		      double theta = lon1 - lon2;
		      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		      dist = Math.acos(dist);
		      dist = rad2deg(dist);
		      dist = dist * 60 * 1.1515 / 1000;
		      return dist;
		}
		
		private double deg2rad(double deg) {
		      return (deg * Math.PI / 180.0);
		}
		
		private double rad2deg(double rad) {
		      return (rad * 180.0 / Math.PI);
		}

	}

}
