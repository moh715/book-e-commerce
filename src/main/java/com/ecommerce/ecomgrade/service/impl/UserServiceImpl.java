package com.ecommerce.ecomgrade.service.impl;
import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.payload.request.RegisterRequest;
import com.ecommerce.ecomgrade.payload.response.UserResponse;
import com.ecommerce.ecomgrade.repository.UserRepository;
import com.ecommerce.ecomgrade.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new APIException("Email already registered: " + request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(),
                saved.getEmail(), saved.getRole());
    }
}
