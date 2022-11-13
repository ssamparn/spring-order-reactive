package com.reactive.microservices.orderservice.model.user;

import lombok.Data;

@Data
public class TransactionRequest {
    private Integer userId;
    private Integer amount;
}
