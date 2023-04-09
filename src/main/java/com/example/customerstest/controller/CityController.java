package com.example.customerstest.controller;

import com.example.customerstest.entity.City;
import com.example.customerstest.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/")
    public ResponseEntity<City> addCityForUser(@ModelAttribute("login") String login, @RequestBody City city) {
        return new ResponseEntity<>(cityService.addCityForUser(login, city), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> getAllCity(@ModelAttribute("login") String login) {
        return new ResponseEntity<>(cityService.userCitiesList(login), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCityForUser(@ModelAttribute("login") String login, @PathVariable Long id) {
        return new ResponseEntity<>(cityService.deleteCityForUser(login, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> setMainCityForUser(@ModelAttribute("login") String login, @PathVariable Long id) {
        cityService.setMainCityForUser(login, id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
