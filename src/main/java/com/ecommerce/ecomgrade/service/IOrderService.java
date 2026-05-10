package com.ecommerce.ecomgrade.service;

import com.ecommerce.ecomgrade.model.Address;
import com.ecommerce.ecomgrade.model.Cart;
import com.ecommerce.ecomgrade.model.OrderItem;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.payload.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(User user, Cart cart, Address address);
    List<OrderResponse> getOrdersByUser(User user);
}