package com.reactive.microservices.userservice.service;

import com.reactive.microservices.userservice.entity.UserTransactionsEntity;
import com.reactive.microservices.userservice.entity.UsersEntity;
import com.reactive.microservices.userservice.model.TransactionRequest;
import com.reactive.microservices.userservice.model.TransactionResponse;
import com.reactive.microservices.userservice.model.TransactionStatus;
import com.reactive.microservices.userservice.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceResponseFactory {

    public User toModel(UsersEntity entity) {
        return User.create(
                entity.getId(),
                entity.getName(),
                entity.getBalance()
        );
    }

    public UsersEntity toEntity(User user) {
        return UsersEntity.create(
                user.getId(),
                user.getName(),
                user.getBalance()
        );
    }

    public UserTransactionsEntity toEntity(TransactionRequest request) {
        UserTransactionsEntity entity = new UserTransactionsEntity();
        entity.setUserId(request.getUserId());
        entity.setAmount(request.getAmount());
        entity.setTransactionDate(LocalDateTime.now());

        return entity;
    }

    public TransactionResponse toModel(TransactionRequest request, TransactionStatus status) {
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setUserId(request.getUserId());
        transactionResponse.setAmount(request.getAmount());
        transactionResponse.setStatus(status);

        return transactionResponse;
    }
}
