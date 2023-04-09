package com.example.customerstest.service;

import com.example.customerstest.entity.City;


import java.util.List;

public interface CityService {
     City addCityForUser(String login, City city);
     boolean deleteCityForUser(String login, Long id);
     List<City> userCitiesList(String Login);
     void setMainCityForUser(String login, Long id);
}
