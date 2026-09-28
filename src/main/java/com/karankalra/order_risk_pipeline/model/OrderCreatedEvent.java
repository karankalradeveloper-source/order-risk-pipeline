package com.karankalra.order_risk_pipeline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String customerId,
        BigDecimal amount,
        String description,
        LocalDateTime receivedAt
) {}
