package com.reactive.microservices.userservice.model;

import lombok.Data;

@Data
public class TransactionResponse {
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
