package com.example.weatherapi.exception;

public class PincodeNotFoundException extends RuntimeException{
    public PincodeNotFoundException(String message) {
        super(message);
    }
}
