package com.ecommerce.ecomgrade.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Order order;

    public OrderItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
