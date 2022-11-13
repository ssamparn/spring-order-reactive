package com.reactive.microservices.orderservice.model.order;

import lombok.Data;

@Data
public class OrderPurchaseRequest {
    private Integer userId;
    private Integer productId;
}
