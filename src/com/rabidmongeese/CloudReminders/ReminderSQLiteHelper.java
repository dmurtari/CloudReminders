package com.rabidmongeese.CloudReminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irakli on 4/12/14.
 */
public class ReminderSQLiteHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "remindersManager";
    private static final String TABLE_REMINDERS = "reminders";

    private static final String KEY_ID = "_id";
    public static final String KEY_TEXT = "reminderText";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    
    private ReminderSQLiteHelper db;
    private SQLiteDatabase mDb;

    public ReminderSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT,"
                + KEY_LONGITUDE + " INTEGER," + KEY_LATITUDE + " INTEGER" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        onCreate(db);
    }

    public String getText(){
    	return KEY_TEXT;
    }
    
    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, reminder.getText());
        values.put(KEY_LONGITUDE, reminder.getLongitude());
        values.put(KEY_LATITUDE, reminder.getLatitude());

        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    public Reminder constructReminder(Cursor cursor) {
        Reminder reminder = new Reminder();

        reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        reminder.setText(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
        reminder.setLongitude(cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)));
        reminder.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)));

        return reminder;
    }

    public Reminder getReminder(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_REMINDERS + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return constructReminder(cursor);
    }

    public List<Reminder> getAllReminders() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        List<Reminder> reminders = new ArrayList<Reminder>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Reminder reminder = constructReminder(cursor);
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        return reminders;
    }
    
    public Cursor getAllRemindersCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS;

        Log.e(LOG, query);

        return db.rawQuery(query, null);
    }

    public int getRemindersCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + TABLE_REMINDERS;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, reminder.getText());
        values.put(KEY_LONGITUDE, reminder.getLongitude());
        values.put(KEY_LATITUDE, reminder.getLatitude());

        return db.update(TABLE_REMINDERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getId()) });
    }

    public void removeReminder(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
