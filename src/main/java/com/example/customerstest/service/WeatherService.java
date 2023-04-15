package com.example.customerstest.service;


import com.example.customerstest.web.responce.WeatherResponse;

public interface WeatherService {
    WeatherResponse receiveWeatherForCityIdAndUser(String login, Long id);
}
