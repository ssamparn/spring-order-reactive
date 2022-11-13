package com.reactive.microservices.userservice.repository;

import com.reactive.microservices.userservice.entity.UserTransactionsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionR2DbcRepository extends ReactiveCrudRepository<UserTransactionsEntity, Integer> {
    Flux<UserTransactionsEntity> findByUserId(Integer userId);
}
