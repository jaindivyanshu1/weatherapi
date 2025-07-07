package com.example.weatherapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;

    private LocalDate date;

    private double temperature;
    private int humidity;
    private String description;

    public WeatherInfo(){}

    public WeatherInfo(String pincode, LocalDate date, double temperature, int humidity, String description) {
        this.date = date;
        this.description=description;
        this.humidity=humidity;
        this.pincode=pincode;
        this.temperature=temperature;
    }

    //id
    public Long getId() {
        return id;
    }

    //pincode
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    //date
    public LocalDate geDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //temperature
    public double getTemperature(){
        return temperature;
    }
    public void setTemperature(double temperature){
        this.temperature=temperature;
    }

    //Humidity
    public int getHumidity(){
        return humidity;
    }
    public void setHumidity(int humidity){
        this.humidity = humidity;
    }

    //description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }

}
