// RegisterRequest.java
package com.ecommerce.ecomgrade.payload.request;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank(message="Username is required")
    @Size(min=3, max=30, message="Username must be 3-30 characters")

    private String username;
    @NotBlank(message="Email is required")
    @Email(message="Invalid email address")

    private String email;
    @NotBlank(message="Password is required")
    @Size(min=6, message="Password must be at least 6 characters")

    private String password;
    public RegisterRequest(){}
    public String getUsername(){return username;} public void setUsername(String
                                                                                  u){this.username=u;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getPassword(){return password;} public void setPassword(String
                                                                                  p){this.password=p;}
}