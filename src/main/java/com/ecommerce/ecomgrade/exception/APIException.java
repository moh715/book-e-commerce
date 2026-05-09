package com.ecommerce.ecomgrade.expception;

public class APIException extends RuntimeException {
    public APIException() {}
    public APIException(String message) { super(message); }
}