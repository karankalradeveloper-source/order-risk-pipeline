package com.karankalra.order_risk_pipeline.controller;

import com.karankalra.order_risk_pipeline.entity.Order;
import com.karankalra.order_risk_pipeline.model.CreateOrderRequestDTO;
import com.karankalra.order_risk_pipeline.model.OrderCreationResult;
import com.karankalra.order_risk_pipeline.model.OrderResponseDTO;
import com.karankalra.order_risk_pipeline.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderRequestDTO orderRequest) {
        OrderCreationResult orderCreationResult = orderService.createOrder(orderRequest);
        Order order = orderCreationResult.getOrder();
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getOrderId());
        orderResponseDTO.setCustomerId(order.getCustomerId());
        orderResponseDTO.setRiskStatus(order.getRiskStatus());
        orderResponseDTO.setAmount(order.getAmount());
        orderResponseDTO.setReceivedAt(order.getReceivedAt());

        HttpStatus status = orderCreationResult.isNewlyCreated() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(orderResponseDTO);
    }
}
