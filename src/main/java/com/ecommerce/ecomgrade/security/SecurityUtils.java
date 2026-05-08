package com.ecommerce.ecomgrade.security;

import com.bookverse.exception.APIException;
import com.bookverse.model.User;
import com.bookverse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Resolves the currently authenticated User entity from the SecurityContext.
 */
@Component
public class SecurityUtils {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new APIException("No authenticated user found.");
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new APIException("Authenticated user not found in database."));
    }
}
