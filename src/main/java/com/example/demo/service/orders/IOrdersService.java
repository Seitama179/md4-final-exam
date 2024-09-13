package com.example.demo.service.orders;

import com.example.demo.model.Orders;
import com.example.demo.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IOrdersService extends IGenerateService<Orders> {
    Page<Orders> findAll(Pageable pageable);

    Page<Orders> findByOrderDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    void updateOrder(Orders existingOrder);

    Orders findOrderById(Long id);
}
