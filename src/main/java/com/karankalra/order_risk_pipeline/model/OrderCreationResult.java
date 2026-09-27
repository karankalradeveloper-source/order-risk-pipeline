package com.karankalra.order_risk_pipeline.model;

import com.karankalra.order_risk_pipeline.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreationResult {

    private final Order order;

    private final boolean newlyCreated;
}
