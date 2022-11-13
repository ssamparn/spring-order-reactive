package com.reactive.microservices.orderservice.model.user;

import lombok.Data;

@Data
public class TransactionResponse {
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
