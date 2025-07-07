package com.example.weatherapi.service;

import com.example.weatherapi.exception.PincodeNotFoundException;
import com.example.weatherapi.exception.WeatherDataUnavailableException;
import com.example.weatherapi.model.PincodeLocation;
import com.example.weatherapi.model.WeatherInfo;
import com.example.weatherapi.repository.PincodeLocationRepository;
import com.example.weatherapi.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherInfoRepository weatherInfoRepository;
    private final PincodeLocationRepository pincodeLocationRepository;
    private final WeatherProviderService weatherProviderService;

    @Autowired
    public WeatherService(WeatherInfoRepository weatherInfoRepository, PincodeLocationRepository pincodeLocationRepository, WeatherProviderService weatherProviderService) {
        this.weatherInfoRepository = weatherInfoRepository;
        this.pincodeLocationRepository = pincodeLocationRepository;
        this.weatherProviderService = weatherProviderService;
    }

    public WeatherInfo getWeatherByPincodeAndDate(String pincode, LocalDate date) {
        Optional<WeatherInfo> cachedWeather = weatherInfoRepository.findByPincodeAndDate(pincode, date);
        if(cachedWeather.isPresent()){
            return cachedWeather.get();
        }

        PincodeLocation location = pincodeLocationRepository.findById(pincode)
                .orElseGet(() -> {
                    PincodeLocation newLoc;

                    try {
                        newLoc = weatherProviderService.getLatLonForPincode(pincode);
                    } catch (Exception e) {
                        throw new PincodeNotFoundException("Pincode not found or invalid" + pincode);
                    }

                    pincodeLocationRepository.save(newLoc);
                    return newLoc;
                });

        if(date.isAfter(LocalDate.now())) {
            throw new WeatherDataUnavailableException("Cannot fetch weather for a future date: " + date);
        }

        WeatherInfo freshWeather;

        try {
            freshWeather = weatherProviderService.getWeatherForLatLon(pincode, date, location.getLatitude(), location.getLongitude());
        } catch (Exception ex) {
            throw new WeatherDataUnavailableException("Weather info could not be fetched for " + pincode);
        }

        weatherInfoRepository.save(freshWeather);

        return freshWeather;

    }


}
