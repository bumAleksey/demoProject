package com.example.customerstest.service.impl;

import com.example.customerstest.dto.WeatherDto;
import com.example.customerstest.entity.City;
import com.example.customerstest.entity.User;
import com.example.customerstest.mapper.WeatherMapper;
import com.example.customerstest.service.CityService;
import com.example.customerstest.service.UserService;
import com.example.customerstest.service.WeatherService;
import com.example.customerstest.web.responce.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    String URL = "https://api.weather.yandex.ru/v2/forecast?hours=true&limit=7&extra=false&lang=ru_RU&geoid=";
    String API_KEY = "0e55d6a7-38d2-4582-b2d9-7bbb86d6ea54";

    private final WeatherMapper weatherMapper;
    private final CityService cityService;
    private final UserService userService;



    @Override
    public WeatherResponse receiveWeatherForCityIdAndUser(String login, Long id) {
        User user = userService.findByLogin(login);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Yandex-API-Key", API_KEY);
            ResponseEntity<String> response = restTemplate.exchange(URL + id, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(response.getBody());
            WeatherDto weatherDto = responseWeatherToDto(response.getBody());
            City city = new City(
                    weatherDto.getCityName(),
                    weatherDto.getCountry(),
                    weatherDto.getLatitude(),
                    weatherDto.getLongitude(),
                    false, user);
            cityService.addCityForUser(login, city);

        return (weatherMapper.WeatherDtoToWeatherResponse(weatherDto));
    }
    private WeatherDto responseWeatherToDto(String response) {
        WeatherDto weatherDto = new WeatherDto();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        JsonObject info = jsonObject.getAsJsonObject("info");
        JsonObject fact = jsonObject.getAsJsonObject("fact");
        JsonObject geoObject = jsonObject.getAsJsonObject("geo_object");
        if(!geoObject.has("district")) {
            weatherDto.setCityId(geoObject.getAsJsonObject("district").get("id").getAsLong());
        }
        weatherDto.setCityName(geoObject.getAsJsonObject("locality").get("name").getAsString());
        weatherDto.setCountry(geoObject.getAsJsonObject("country").get("name").getAsString());
        weatherDto.setLatitude(info.get("lat").getAsDouble());
        weatherDto.setLongitude(info.get("lon").getAsDouble());
        weatherDto.setTemperature(fact.get("temp").getAsInt());
        weatherDto.setFeelsLikeTemperature(fact.get("feels_like").getAsInt());
        weatherDto.setHumidity(fact.get("humidity").getAsInt());
        weatherDto.setWindSpeed(fact.get("wind_speed").getAsDouble());
        weatherDto.setWindDirection(fact.get("wind_dir").getAsString());
        weatherDto.setWeatherCondition(fact.get("condition").getAsString());
        weatherDto.setSunrise(jsonObject.getAsJsonArray("forecasts").get(0).getAsJsonObject().get("sunrise").getAsString());
        weatherDto.setSunset(jsonObject.getAsJsonArray("forecasts").get(0).getAsJsonObject().get("sunset").getAsString());
        weatherDto.setPressure(fact.get("pressure_mm").getAsDouble());
        return weatherDto;
    }
}