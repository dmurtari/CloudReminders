package com.rabidmongeese.CloudReminders;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */

    private EditText email, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        Button login = (Button) findViewById(R.id.bLogin);

        login.setOnClickListener(this);


        /*
        //Test code for database
        ReminderSQLiteHelper db = new ReminderSQLiteHelper(this);
        Log.d("Insert", "Inserting...");

        db.addReminder(new Reminder("This is one text", 10.2, 30.4));
        db.addReminder(new Reminder("This is second text", 14.2, 34.4));
        db.addReminder(new Reminder("This is third text", 18.2, 38.4));
        db.addReminder(new Reminder("This is fourth text", 22.2, 42.4));
        db.addReminder(new Reminder("This is fifth text", 26.2, 46.4));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Reminder> reminders = db.getAllReminders();

        for (Reminder rm : reminders) {
            String log = "Id: "+rm.getId()+" , Text: " + rm.getText() + " , Longitude: "
                    + rm.getLongitude() + " , Latitude: " + rm.getLatitude();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        */

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                //Log the user in
                break;
            default:
                break;
        }

    }
}
