package com.example.customerstest.mapper;

import com.example.customerstest.dto.WeatherDto;
import com.example.customerstest.web.responce.WeatherResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    WeatherResponse WeatherDtoToWeatherResponse(WeatherDto weatherDto);
    
}
