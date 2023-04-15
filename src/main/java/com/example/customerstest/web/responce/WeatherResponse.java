package com.example.customerstest.web.responce;


import lombok.Data;

@Data
public class WeatherResponse {
    private String cityName; // Название города
    private String country; // Название страны
    private int temperature; // Температура
    private int feelsLikeTemperature;
    private String weatherCondition; // Описание погоды
    private Double windSpeed; // Скорость ветра
    private String windDirection; // Направление ветра
    private double pressure; // Атмосферное давление
    private int humidity; // Влажность
    private String sunrise;
    private String sunset;

}
