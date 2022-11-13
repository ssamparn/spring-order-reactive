package com.reactive.microservices.userservice.model;

import lombok.Data;

@Data
public class TransactionRequest {
    private Integer userId;
    private Integer amount;
}
