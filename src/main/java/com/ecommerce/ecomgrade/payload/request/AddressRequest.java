package com.ecommerce.ecomgrade.payload.request;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zipcode is required")
    private String zipCode;   // ← was "zipcode", now matches model

    @NotBlank(message = "Country is required")
    private String country;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }          // ← add this
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }  // ← add this
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}