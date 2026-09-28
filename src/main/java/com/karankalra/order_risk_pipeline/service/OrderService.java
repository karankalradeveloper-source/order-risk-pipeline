package com.karankalra.order_risk_pipeline.service;

import com.karankalra.order_risk_pipeline.entity.Order;
import com.karankalra.order_risk_pipeline.enums.RiskStatus;
import com.karankalra.order_risk_pipeline.model.CreateOrderRequestDTO;
import com.karankalra.order_risk_pipeline.model.OrderCreatedEvent;
import com.karankalra.order_risk_pipeline.model.OrderCreationResult;
import com.karankalra.order_risk_pipeline.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
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

        try {
            CompletableFuture<SendResult<String, OrderCreatedEvent>> future = sendOrderCreatedEvent(savedOrder);
            if(future != null) {
                future.thenAccept(result -> {
                    System.out.println("Order created event sent successfully for orderId: " + savedOrder.getOrderId());
                }).exceptionally(ex -> {
                    System.err.println("Failed to send order created event for orderId: " + savedOrder.getOrderId() + ". Error: " + ex.getMessage());
                    return null;
                });
            }
        } catch (Exception e) {
            System.err.println("Failed to send order created event for orderId: " + savedOrder.getOrderId() + ". Error: " + e.getMessage());
        }

        return new OrderCreationResult(savedOrder, true);
    }

    public CompletableFuture sendOrderCreatedEvent(Order order) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                order.getOrderId(),
                order.getCustomerId(),
                order.getAmount(),
                order.getDescription(),
                order.getReceivedAt()
        );
        return kafkaTemplate.send("order.created", order.getCustomerId(), orderCreatedEvent);
    }
}
