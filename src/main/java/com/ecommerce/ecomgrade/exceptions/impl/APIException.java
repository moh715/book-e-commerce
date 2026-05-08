package com.ecommerce.ecomgrade.exceptions.impl;

public class APIException extends RuntimeException {
    public APIException() {}
    public APIException(String message) { super(message); }
}