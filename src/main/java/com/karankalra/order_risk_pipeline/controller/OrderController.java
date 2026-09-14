package com.karankalra.order_risk_pipeline.controller;

import com.karankalra.order_risk_pipeline.entity.Order;
import com.karankalra.order_risk_pipeline.model.CreateOrderRequestDTO;
import com.karankalra.order_risk_pipeline.model.OrderResponseDTO;
import com.karankalra.order_risk_pipeline.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public OrderResponseDTO createOrder(CreateOrderRequestDTO orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return new OrderResponseDTO();
    }
}
