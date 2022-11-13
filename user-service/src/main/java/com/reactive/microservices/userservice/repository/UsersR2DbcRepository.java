package com.reactive.microservices.userservice.repository;

import com.reactive.microservices.userservice.entity.UsersEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersR2DbcRepository extends ReactiveCrudRepository<UsersEntity, Integer> {

    @Query("update users " +
            "set balance = balance - :amount " +
            "where id = :userId " +
            "and balance >= :amount"
    )
    @Modifying
    Mono<Boolean> updateUsersBalance(int userId, int amount);
}
