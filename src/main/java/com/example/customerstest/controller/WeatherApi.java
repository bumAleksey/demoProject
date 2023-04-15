package com.example.customerstest.controller;

import com.example.customerstest.service .WeatherService;
import com.example.customerstest.web.responce.WeatherResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherApi {

    private final WeatherService weatherService;

    public WeatherApi(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{id}")
    public WeatherResponse getWeatherForCoordinates(@ModelAttribute("login") String login, @PathVariable long id) {
       return weatherService.receiveWeatherForCityIdAndUser(login, id);
    }

}
