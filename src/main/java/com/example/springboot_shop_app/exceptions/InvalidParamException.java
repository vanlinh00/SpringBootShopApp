package com.example.springboot_shop_app.exceptions;

public class InvalidParamException extends Exception {
    public InvalidParamException(String message) {
        super(message);
    }
}
