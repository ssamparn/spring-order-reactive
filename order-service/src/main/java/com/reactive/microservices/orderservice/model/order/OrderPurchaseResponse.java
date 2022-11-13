package com.reactive.microservices.orderservice.model.order;

import lombok.Data;

@Data
public class OrderPurchaseResponse {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private OrderStatus status;
}
