package com.m2dl.mobebmp.mobe_bmp;

/**
 * Created by montasser on 13/03/2018.
 */

public class Batiment {

    private String name = "";
    private double longitude = 0D;
    private double latitude = 0D;

    public Batiment(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Batiment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
