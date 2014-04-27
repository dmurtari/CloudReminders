package com.rabidmongeese.CloudReminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TemporaryActivity extends Activity {
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_activity);
        
        
        setListeners();
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
