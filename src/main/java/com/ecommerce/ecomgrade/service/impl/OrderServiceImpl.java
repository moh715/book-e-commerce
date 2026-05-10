package com.ecommerce.ecomgrade.service.impl;
import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.*;
import com.ecommerce.ecomgrade.payload.response.OrderResponse;
import com.ecommerce.ecomgrade.repository.*;
import com.ecommerce.ecomgrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private BookRepository bookRepository;

    @Override
    public OrderResponse createOrder(User user, Cart cart, Address address) {
        if (cart.getItems().isEmpty()) throw new APIException("Cart is empty.");

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setBook(ci.getBook());
            oi.setQuantity(ci.getQuantity());
            oi.setPriceAtPurchase(ci.getBook().getPrice());   // snapshot
            oi.setOrder(order);
            orderItems.add(oi);

            // decrement stock
            Book b = ci.getBook();
            b.setStock(b.getStock() - ci.getQuantity());
            bookRepository.save(b);

            total += ci.getBook().getPrice() * ci.getQuantity();
        }

        order.setTotalPrice(total);
        order.setItems(orderItems);
        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(User user) {
        return orderRepository.findByUser(user).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId()); r.setOrderDate(order.getOrderDate());
        r.setTotalPrice(order.getTotalPrice());
        if (order.getItems() != null) {
            List<OrderResponse.OrderItemResponse> list = order.getItems().stream().map(i -> {
                OrderResponse.OrderItemResponse ir = new OrderResponse.OrderItemResponse();
                ir.setBookTitle(i.getBook().getTitle());
                ir.setQuantity(i.getQuantity());
                ir.setUnitPrice(i.getBook().getPrice());
                return ir;
            }).collect(Collectors.toList());
            r.setItems(list);
        }
        return r;
    }




}
