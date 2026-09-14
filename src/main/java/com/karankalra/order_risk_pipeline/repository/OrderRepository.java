package com.karankalra.order_risk_pipeline.repository;

import com.karankalra.order_risk_pipeline.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(UUID orderId);
}
