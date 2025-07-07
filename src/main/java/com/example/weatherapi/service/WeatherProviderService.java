package com.example.weatherapi.service;

import com.example.weatherapi.model.PincodeLocation;
import com.example.weatherapi.model.WeatherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Service
public class WeatherProviderService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public PincodeLocation getLatLonForPincode(String pincode) {
        String url = String.format("https://api.openweathermap.org/geo/1.0/zip?zip=%s,in&appid=%s", pincode, apiKey);
        Map<String, Object> res = restTemplate.getForObject(url, Map.class);
        if(res == null || !res.containsKey("lat") || !res.containsKey("lon")) {
            throw new RuntimeException("Invalid Response");
        }

        double lat = Double.parseDouble(res.get("lat").toString());
        double lon = Double.parseDouble(res.get("lon").toString());

        return new PincodeLocation(pincode, lat, lon);
    }

    public WeatherInfo getWeatherForLatLon(String pincode, LocalDate date, double lat, double lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric", lat, lon, apiKey);

        Map<String, Object> res = restTemplate.getForObject(url, Map.class);

        if(res == null || !res.containsKey("main")){
            throw new RuntimeException("Invalid response");
        }

        Map<String, Object> main = (Map<String, Object>) res.get("main");
        double temp = Double.parseDouble(main.get("temp").toString());
        int humidity = Integer.parseInt(main.get("humidity").toString());

        String desc = "N/A";
        if(res.containsKey("weather")) {
            var weatherList = (java.util.List<Map<String, Object>>) res.get("weather");
            if(!weatherList.isEmpty()) {
                desc = weatherList.get(0).get("description").toString();
            }
        }

        return new WeatherInfo(pincode, date, temp, humidity,desc);

    }
}
