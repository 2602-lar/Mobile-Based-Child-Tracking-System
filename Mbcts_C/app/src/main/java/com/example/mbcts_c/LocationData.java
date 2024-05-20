package com.example.mbcts_c;

public class LocationData {
    private double latitude;
    private double longitude;

    private String child_id;

    public LocationData(double latitude, double longitude, String child_id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.child_id = child_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }
}
