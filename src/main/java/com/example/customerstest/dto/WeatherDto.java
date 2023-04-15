package com.example.customerstest.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private long cityId;
    private String cityName;
    private String country;
    private Double latitude;
    private Double longitude;
    private int temperature;
    private int feelsLikeTemperature;
    private int humidity;
    private Double windSpeed;
    private String windDirection;
    private String weatherCondition;
    private String sunrise;
    private String sunset;
    private Double pressure;
}
