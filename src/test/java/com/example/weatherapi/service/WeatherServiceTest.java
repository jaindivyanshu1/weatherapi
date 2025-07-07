package com.example.weatherapi.service;

import com.example.weatherapi.exception.PincodeNotFoundException;
import com.example.weatherapi.model.PincodeLocation;
import com.example.weatherapi.model.WeatherInfo;
import com.example.weatherapi.repository.PincodeLocationRepository;
import com.example.weatherapi.repository.WeatherInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    private WeatherInfoRepository weatherInfoRepo;
    private PincodeLocationRepository locationRepo;
    private WeatherProviderService providerService;

    private WeatherService weatherService;

    @BeforeEach
    void setup() {
        weatherInfoRepo = mock(WeatherInfoRepository.class);
        locationRepo = mock(PincodeLocationRepository.class);
        providerService = mock(WeatherProviderService.class);
        weatherService = new WeatherService(weatherInfoRepo, locationRepo, providerService);
    }

    @Test
    void shouldReturnCachedWeatherIfExists() {
        LocalDate date = LocalDate.of(2020, 10, 15);
        String pincode = "411014";
        WeatherInfo mockWeather = new WeatherInfo(pincode, date, 25.0, 80, "clear");

        when(weatherInfoRepo.findByPincodeAndDate(pincode, date)).thenReturn(Optional.of(mockWeather));

        WeatherInfo result = weatherService.getWeatherByPincodeAndDate(pincode, date);

        assertEquals(25.0, result.getTemperature());
        verify(weatherInfoRepo, times(1)).findByPincodeAndDate(pincode, date);
        verifyNoInteractions(providerService);
    }

    @Test
    void shouldFetchFromAPIWhenNotCached() {
        LocalDate date = LocalDate.of(2020, 10, 15);
        String pincode = "411014";

        when(weatherInfoRepo.findByPincodeAndDate(pincode, date)).thenReturn(Optional.empty());
        when(locationRepo.findById(pincode)).thenReturn(Optional.empty());

        PincodeLocation loc = new PincodeLocation(pincode, 18.55, 73.93);
        when(providerService.getLatLonForPincode(pincode)).thenReturn(loc);

        WeatherInfo newWeather = new WeatherInfo(pincode, date, 28.3, 60, "cloudy");
        when(providerService.getWeatherForLatLon(pincode, date, loc.getLatitude(), loc.getLongitude()))
                .thenReturn(newWeather);

        WeatherInfo result = weatherService.getWeatherByPincodeAndDate(pincode, date);

        assertEquals("cloudy", result.getDescription());
        verify(weatherInfoRepo).save(newWeather);
    }

    @Test
    void shouldThrowIfPincodeInvalid() {
        when(weatherInfoRepo.findByPincodeAndDate("000000", LocalDate.of(2020, 10, 15)))
                .thenReturn(Optional.empty());

        when(locationRepo.findById("000000")).thenReturn(Optional.empty());

        when(providerService.getLatLonForPincode("000000"))
                .thenThrow(new RuntimeException("not found"));

        assertThrows(PincodeNotFoundException.class, () ->
                weatherService.getWeatherByPincodeAndDate("000000", LocalDate.of(2020, 10, 15)));
    }
}
