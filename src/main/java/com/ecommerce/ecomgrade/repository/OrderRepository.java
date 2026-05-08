package com.ecommerce.ecomgrade.repository;
import com.ecommerce.ecomgrade.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}