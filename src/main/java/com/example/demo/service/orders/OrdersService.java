package com.example.demo.service.orders;

import com.example.demo.model.Orders;
import com.example.demo.repository.IOrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService implements IOrdersService {
    private final IOrdersRepository ordersRepository;
    public OrdersService(IOrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Override
    public List<Orders> findAll() {
        return List.of();
    }

    @Override
    public Page<Orders> findByOrderDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return ordersRepository.findByOrderDateBetween(startDateTime, endDateTime, pageable);
    }

    @Override
    public void updateOrder(Orders existingOrder) {
        ordersRepository.save(existingOrder);
    }

    @Override
    public Orders findOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return ordersRepository.findById(id);
    }
}
