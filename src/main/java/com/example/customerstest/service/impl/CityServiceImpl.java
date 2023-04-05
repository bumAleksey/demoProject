package com.example.customerstest.service.impl;

import com.example.customerstest.entity.City;
import com.example.customerstest.entity.User;
import com.example.customerstest.repository.CityRepository;
import com.example.customerstest.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final UserServiceImpl userService;


    @Autowired
    public CityServiceImpl(CityRepository cityRepository, UserServiceImpl userService) {
        this.cityRepository = cityRepository;
        this.userService = userService;
    }

    @Override
    public City addCityForUser(String login, City city) {
        User user = userService.findByLogin(login);
        List<City> list = userCitiesList(login);
        //проверям координаты и получем город
        City coordinatedCity = checkCityForCoordinates(list, city.getLatitude(), city.getLongitude());
        //убираем isMain с true.. тк листа с City в базе нет
        checkMainCityForUser(list);
        if (coordinatedCity != null) {
            coordinatedCity.setMain(true);
            return cityRepository.save(coordinatedCity);
        } else {
            city.setMain(true);
            city.setUser(user);
            cityRepository.save(city);
        }
        return city;
    }

    @Override
    public boolean deleteCityForUser(String login, Long id) {
        cityRepository.delete(cityRepository.getById(id));
        return true;
    }


    @Override
    public void setMainCityForUser(String login, Long id) {
        checkMainCityForUser(cityRepository.findCitiesByUserLogin(login));
        City city = cityRepository.findById(id).get();
        city.setMain(true);
        cityRepository.save(city);
    }

    @Override
    public List<City> userCitiesList(String login) {
        return cityRepository.findCitiesByUserLogin(login);
    }


    private City checkMainCityForUser(List<City> userCityList) {
        City city = userCityList.stream().filter(City::isMain).findFirst().orElse(null);
        if (city == null) return null;
        city.setMain(false);
        return cityRepository.save(city);
    }


    private City checkCityForCoordinates(List<City> list, long latitude, long longitude) {
        return list.stream()
                .filter(city -> city.getLatitude() == latitude
                        && city.getLongitude() == longitude).findFirst().orElse(null);
    }

    //генерация городов для теста

    @PostConstruct
    public void init() {
        User user = new User();
        user.setId(1L);
        user.setLogin("Valmont");
        user.setPassword("123456");
        City city = new City();
        city.setName("Voronezh");
        city.setLongitude(123456);
        city.setLatitude(123456);
        city.setCountry("RUSSIA");
        city.setUser(user);
        cityRepository.save(city);

        City city1 = new City();
        city1.setName("sochi");
        city1.setLongitude(543255);
        city1.setLatitude(665852);
        city1.setCountry("RUSSIA");
        city1.setMain(true);
        city1.setUser(user);
        cityRepository.save(city1);

    }

}
