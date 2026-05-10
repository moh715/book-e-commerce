package com.ecommerce.ecomgrade.repository;

import com.ecommerce.ecomgrade.model.Book;
import com.ecommerce.ecomgrade.model.Cart;
import com.ecommerce.ecomgrade.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
}
