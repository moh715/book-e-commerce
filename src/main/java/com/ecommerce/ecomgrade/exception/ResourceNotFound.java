package com.ecommerce.ecomgrade.exception;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String resource, String field, String value) {
        super(String.format("%s not found with %s: %s", resource, field, value));
    }

    public ResourceNotFound(String resource, String field, Long id) {
        super(String.format("%s not found with %s: %d", resource, field, id));
    }

    public ResourceNotFound(String resource, String field, long id) {
        super(String.format("%s not found with %s: %d", resource, field, id));
    }
}
