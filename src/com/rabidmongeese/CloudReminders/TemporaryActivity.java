package com.rabidmongeese.CloudReminders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TemporaryActivity extends Activity {
	
	private ReminderSQLiteHelper db;
	private SimpleCursorAdapter reminderAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_activity);
        
        db = new ReminderSQLiteHelper(this);
        
        Log.w("Creating list view ", "ListView");
        displayListView();
        setListeners();
	}
	
	private void displayListView(){
		Log.w("Adding ", "Hello");
		db.addReminder(new Reminder("hello", 105, 40));
		
		Cursor cursor = db.getAllRemindersCursor();
		
		
		// Database columns to read
		String[] columns = new String[]{
				db.KEY_TEXT
		};
				
		// Views that define where the columns will go
		int[] to = new int[]{
				R.id.reminder_item
		};
				
		// Create the adapter
		reminderAdapter = new SimpleCursorAdapter(
				this,
				R.layout.reminder_list_row,
				cursor,
				columns,
				to,
				0);
		
		ListView reminderView = (ListView) findViewById(R.id.reminderList);
		reminderView.setAdapter(reminderAdapter);
		
	}
	
	private void setListeners(){
		
		Button createReminder = (Button) findViewById(R.id.createReminderButton);
		Button mapButton = (Button) findViewById(R.id.mapButton);
		
		createReminder.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(TemporaryActivity.this, CreateReminderActivity.class ));
			}
			
		});
		
		mapButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(TemporaryActivity.this, MapActivity.class ));
			}
			
		});
	}

}
