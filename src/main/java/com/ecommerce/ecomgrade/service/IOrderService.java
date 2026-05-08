package com.ecommerce.ecomgrade.service;

import com.ecommerce.ecomgrade.model.OrderItem;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.payload.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(User user, List<OrderItem> items, Double totalPrice);
    List<OrderResponse> getOrdersByUser(User user);
}