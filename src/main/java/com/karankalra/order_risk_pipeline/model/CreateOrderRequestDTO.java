package com.karankalra.order_risk_pipeline.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {

    @NotNull
    private UUID orderId;

    @NotNull
    private String customerId;

    @NotNull
    @Positive
    private BigDecimal amount;

    private String description;

    private String shippingAddress;
}
