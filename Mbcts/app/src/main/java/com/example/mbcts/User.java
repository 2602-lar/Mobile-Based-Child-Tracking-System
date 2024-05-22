package com.example.mbcts;

public class User {
    private String username;
    private String password;

    private String latitude;
    private String longitude;
    private Double distance;

    public User(String username, String password, String latitude, String longitude, Double distance) {
        this.username = username;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
