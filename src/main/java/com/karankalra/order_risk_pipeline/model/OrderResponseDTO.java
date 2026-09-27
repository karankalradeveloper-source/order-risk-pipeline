package com.karankalra.order_risk_pipeline.model;

import com.karankalra.order_risk_pipeline.enums.RiskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private UUID orderId;

    private String customerId;

    private RiskStatus riskStatus;

    private BigDecimal amount;

    private LocalDateTime receivedAt;
}
