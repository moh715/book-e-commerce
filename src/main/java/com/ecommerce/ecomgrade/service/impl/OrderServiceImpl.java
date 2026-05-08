package com.ecommerce.ecomgrade.service.impl;

import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.Order;
import com.ecommerce.ecomgrade.model.OrderItem;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.payload.response.OrderResponse;
import com.ecommerce.ecomgrade.repository.BookRepository;
import com.ecommerce.ecomgrade.repository.OrderItemRepository;
import com.ecommerce.ecomgrade.repository.OrderRepository;
import com.ecommerce.ecomgrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    private OrderResponse toResponse(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setOrderDate(order.getOrderDate());
        resp.setTotalPrice(order.getTotalPrice());

        if (order.getItems() != null) {
            List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
                    .map(item -> {
                        OrderResponse.OrderItemResponse ir = new OrderResponse.OrderItemResponse();
                        ir.setBookTitle(item.getBook().getTitle());
                        ir.setQuantity(item.getQuantity());
                        ir.setUnitPrice(item.getBook().getPrice());
                        return ir;
                    })
                    .collect(Collectors.toList());
            resp.setItems(itemResponses);
        }
        return resp;
    }

    @Override
    public OrderResponse createOrder(User user, List<OrderItem> items, Double totalPrice) {
        if (items == null || items.isEmpty()) {
            throw new APIException("Cannot place an order with an empty cart.");
        }
        if (totalPrice == null || totalPrice <= 0) {
            throw new APIException("Order total must be positive.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(totalPrice);
        order.setItems(items);

        Order saved = orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrder(saved);
            orderItemRepository.save(item);

            // Decrement stock
            var book = item.getBook();
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        }

        return toResponse(saved);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(User user) {
        if (user == null) {
            throw new APIException("User must be provided to retrieve orders.");
        }
        return orderRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
