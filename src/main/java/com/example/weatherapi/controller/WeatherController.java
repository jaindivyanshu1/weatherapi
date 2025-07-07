package com.example.weatherapi.controller;

import com.example.weatherapi.model.WeatherInfo;
import com.example.weatherapi.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    @Operation(summary = "Get weather information", description = "Returns weather details for a given pincode and date")
    public WeatherInfo getWeather(
            @Parameter(description = "Valid Indian pincode", example = "411014")
            @RequestParam String pincode,

            @Parameter(description = "Date in YYYY-MM-DD format", example = "2025-07-08")
            @RequestParam("for_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forDate
            ) {
        return weatherService.getWeatherByPincodeAndDate(pincode, forDate);
    }

}
