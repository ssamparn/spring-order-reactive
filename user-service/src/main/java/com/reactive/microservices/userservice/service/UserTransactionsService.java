package com.reactive.microservices.userservice.service;

import com.reactive.microservices.userservice.entity.UserTransactionsEntity;
import com.reactive.microservices.userservice.model.TransactionRequest;
import com.reactive.microservices.userservice.model.TransactionResponse;
import com.reactive.microservices.userservice.model.TransactionStatus;
import com.reactive.microservices.userservice.repository.UserTransactionR2DbcRepository;
import com.reactive.microservices.userservice.repository.UsersR2DbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionsService {

    private final UsersR2DbcRepository usersRepository;
    private final UserTransactionR2DbcRepository userTransactionRepository;
    private final UserServiceResponseFactory responseFactory;

    public Mono<TransactionResponse> createTransactions(TransactionRequest request) {
        Integer userId = request.getUserId();
        Integer amount = request.getAmount();

        return this.usersRepository.updateUsersBalance(userId, amount)
                .filter(Boolean::booleanValue)
                .map(aBoolean -> responseFactory.toEntity(request))
                .flatMap(userTransactionRepository::save)
                .map(userTransactionsEntity -> responseFactory.toModel(request, TransactionStatus.APPROVED))
                .defaultIfEmpty(responseFactory.toModel(request, TransactionStatus.DECLINED));
    }

    public Flux<UserTransactionsEntity> getByUserId(Integer userId) {
        return this.userTransactionRepository.findByUserId(userId);
    }

}
