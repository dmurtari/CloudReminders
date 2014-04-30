package com.rabidmongeese.CloudReminders;

import java.io.IOException;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class CreateReminderActivity extends Activity implements OnClickListener {
	private EditText reminderName, reminderAddress, reminderLat, reminderLng;
	private ReminderSQLiteHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_reminder);
		
		db = new ReminderSQLiteHelper(this);

		reminderName = (EditText) findViewById(R.id.reminderName);
		reminderAddress = (EditText) findViewById(R.id.reminderAddress);
		reminderLat = (EditText) findViewById(R.id.etLatitude);
		reminderLng = (EditText) findViewById(R.id.etLongitude);
		
		Button submitButton = (Button) findViewById(R.id.bCreateReminderButton);
		Button sb = (Button) findViewById(R.id.bLatAdd);
		sb.setOnClickListener(this);
		
		submitButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_reminder, menu);
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
			View rootView = inflater.inflate(R.layout.create_reminder,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bCreateReminderButton) {
			String title = reminderName.getText().toString();
			String address = reminderAddress.getText().toString();
			GeoPoint p = getLatLong(address);
			double latitude = p.getLatitudeE6();
			double longitude = p.getLongitudeE6();
			db.addReminder(new Reminder(title, longitude, latitude));
			Log.w("CreateReminder", longitude + " " + latitude);
		}
		else if (v.getId() == R.id.bLatAdd) {
			double latitude = Double.parseDouble(reminderLat.getText().toString());
			double longitude = Double.parseDouble(reminderLng.getText().toString());
			String title = reminderName.getText().toString();
			db.addReminder(new Reminder(title, longitude, latitude));
			Log.w("CreateReminder", longitude + " " + latitude);
		}
		
	}
	
	protected GeoPoint getLatLong(String strAddress){
		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
		    address = coder.getFromLocationName(strAddress,5);
		    if (address == null) {
		        return null;
		    }

		    Address location = address.get(0);
		    location.getLatitude();
		    location.getLongitude();

		    GeoPoint p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
		                      (int) (location.getLongitude() * 1E6));
		    
		    return p1;

		} catch(IOException e) {
		// Put up alert dialog if the address is not valid
			return null;

		}

	}

}
