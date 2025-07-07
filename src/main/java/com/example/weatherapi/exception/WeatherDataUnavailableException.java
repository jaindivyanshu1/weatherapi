package com.example.weatherapi.exception;

public class WeatherDataUnavailableException extends RuntimeException{
    public WeatherDataUnavailableException(String message) {
        super(message);
    }
}
