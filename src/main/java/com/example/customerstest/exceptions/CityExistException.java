package com.example.customerstest.exceptions;

public class CityExistException extends RuntimeException{
    public CityExistException(String message) {
        super(message);
    }
}
