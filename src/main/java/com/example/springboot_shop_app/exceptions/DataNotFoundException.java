package com.example.springboot_shop_app.exceptions;

public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
}
