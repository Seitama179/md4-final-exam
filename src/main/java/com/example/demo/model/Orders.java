package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    private LocalDateTime orderDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be a positive number")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Transient
    private String formattedDate;

    public Orders() {
    }

    public Orders(Long orderId, LocalDateTime orderDate, int quantity, Product product) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return this.product.getPrice() * this.quantity;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
