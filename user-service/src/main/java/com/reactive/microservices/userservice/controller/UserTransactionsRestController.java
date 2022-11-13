package com.reactive.microservices.userservice.controller;

import com.reactive.microservices.userservice.entity.UserTransactionsEntity;
import com.reactive.microservices.userservice.model.TransactionRequest;
import com.reactive.microservices.userservice.model.TransactionResponse;
import com.reactive.microservices.userservice.service.UserTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserTransactionsRestController {

    private final UserTransactionsService userTransactionsService;

    @PostMapping("/transactions")
    public Mono<TransactionResponse> createUserTransactions(@RequestBody Mono<TransactionRequest> requestMono) {
        return requestMono
                .flatMap(userTransactionsService::createTransactions);
    }

    @GetMapping("transactions/{userId}")
    public Flux<UserTransactionsEntity> getByUserId(@PathVariable(value = "userId") Integer userId) {
        return this.userTransactionsService.getByUserId(userId);
    }
}
