package com.ecommerce.ecomgrade.payload.request;

import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {
    @NotNull(message = "Please select a shipping address.")
    private Long addressId;

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
}
