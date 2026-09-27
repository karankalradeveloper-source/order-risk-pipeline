package com.karankalra.order_risk_pipeline.service;

import com.karankalra.order_risk_pipeline.entity.Order;
import com.karankalra.order_risk_pipeline.enums.RiskStatus;
import com.karankalra.order_risk_pipeline.model.CreateOrderRequestDTO;
import com.karankalra.order_risk_pipeline.model.OrderCreationResult;
import com.karankalra.order_risk_pipeline.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderCreationResult createOrder(CreateOrderRequestDTO orderRequest) {
        Optional<Order> existingOrder = orderRepository.findByOrderId(orderRequest.getOrderId());
        if(existingOrder.isPresent()) {
            return new OrderCreationResult(existingOrder.get(), false);
        }
        Order newOrder = Order.builder()
                .orderId(orderRequest.getOrderId())
                .customerId(orderRequest.getCustomerId())
                .amount(orderRequest.getAmount())
                .description(orderRequest.getDescription())
                .shippingAddress(orderRequest.getShippingAddress())
                .riskStatus(RiskStatus.PENDING)
                .build();
        Order savedOrder = orderRepository.save(newOrder);
        return new OrderCreationResult(savedOrder, true);
    }
}
