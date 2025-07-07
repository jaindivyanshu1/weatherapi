package com.example.weatherapi.controller;

import com.example.weatherapi.model.WeatherInfo;
import com.example.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherData() throws Exception {
        WeatherInfo info = new WeatherInfo("411014", LocalDate.of(2020, 10, 15), 28.5, 70, "sunny");

        when(weatherService.getWeatherByPincodeAndDate("411014", LocalDate.of(2020, 10, 15)))
                .thenReturn(info);

        mockMvc.perform(get("/weather")
                        .param("pincode", "411014")
                        .param("for_date", "2020-10-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pincode").value("411014"))
                .andExpect(jsonPath("$.temperature").value(28.5))
                .andExpect(jsonPath("$.description").value("sunny"));
    }
}
