package com.rabidmongeese.CloudReminders;

/**
 * Created by irakli on 4/12/14.
 */
public class Reminder {

    private int id;
    private String text;
    private double longitude, latitude;

    public Reminder(int id, String text, double longitude, double latitude) {
        this.id = id;
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Reminder(String text, double longitude, double latitude) {
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
}
