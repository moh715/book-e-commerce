package com.ecommerce.ecomgrade.service;

import com.ecommerce.ecomgrade.model.Cart;

public interface ICartService {
    Cart getOrCreateCart();           // returns the cart for the logged-in user
    void addItem(Long bookId, int qty);
    void updateItem(Long cartItemId, int qty);
    void removeItem(Long cartItemId);
    void clearCart();
}
