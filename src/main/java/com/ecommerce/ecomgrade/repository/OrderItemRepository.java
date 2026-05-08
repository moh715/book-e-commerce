package com.ecommerce.ecomgrade.repository;
import com.ecommerce.ecomgrade.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {



}