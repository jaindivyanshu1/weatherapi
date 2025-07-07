package com.example.weatherapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PincodeLocation {
    @Id
    private String pincode;

    private double latitude;
    private double longitude;

    public PincodeLocation() {}

    public PincodeLocation(String pincode, double latitude, double longitude) {
        this.pincode = pincode;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    //Pincode
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    //latitude
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //longitude
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
