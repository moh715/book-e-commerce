package com.ecommerce.ecomgrade.repository;

import com.ecommerce.ecomgrade.model.Cart;
import com.ecommerce.ecomgrade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
