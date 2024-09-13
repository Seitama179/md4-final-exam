package com.example.demo.repository;

import com.example.demo.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE o.order_date BETWEEN :startDateTime AND :endDateTime")
    Page<Orders> findByOrderDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
