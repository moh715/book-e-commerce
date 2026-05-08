package com.ecommerce.ecomgrade.service;

import com.ecommerce.ecomgrade.payload.request.RegisterRequest;
import com.ecommerce.ecomgrade.payload.response.UserResponse;

public interface IUserService {
    UserResponse registerUser(RegisterRequest request);
}