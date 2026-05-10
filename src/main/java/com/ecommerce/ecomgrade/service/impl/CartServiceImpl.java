package com.ecommerce.ecomgrade.service.impl;

import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.*;
import com.ecommerce.ecomgrade.repository.*;
import com.ecommerce.ecomgrade.security.SecurityUtils;
import com.ecommerce.ecomgrade.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private SecurityUtils securityUtils;

    @Override
    public Cart getOrCreateCart() {
        User user = securityUtils.getCurrentUser();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Override
    public void addItem(Long bookId, int qty) {
        if (qty <= 0) throw new APIException("Quantity must be at least 1.");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new APIException("Book not found."));
        if (book.getStock() < qty)
            throw new APIException("Not enough stock for: " + book.getTitle());

        Cart cart = getOrCreateCart();
        CartItem existing = cartItemRepository.findByCartAndBook(cart, book).orElse(null);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + qty);
            cartItemRepository.save(existing);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setBook(book);
            item.setQuantity(qty);
            cartItemRepository.save(item);
        }
    }

    @Override
    public void updateItem(Long cartItemId, int qty) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new APIException("Cart item not found."));
        if (qty <= 0) { cartItemRepository.delete(item); return; }
        item.setQuantity(qty);
        cartItemRepository.save(item);
    }

    @Override
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart() {
        Cart cart = getOrCreateCart();
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
