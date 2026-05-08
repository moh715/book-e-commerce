package com.ecommerce.ecomgrade.service.impl;

import com.bookverse.exception.APIException;
import com.bookverse.model.Order;
import com.bookverse.model.OrderItem;
import com.bookverse.model.User;
import com.bookverse.payload.response.OrderResponse;
import com.bookverse.repository.BookRepository;
import com.bookverse.repository.OrderItemRepository;
import com.bookverse.repository.OrderRepository;
import com.bookverse.service.IOrderService;
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
