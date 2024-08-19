package com.reactive.microservices.userservice.service;

import com.reactive.microservices.userservice.entity.UsersEntity;
import com.reactive.microservices.userservice.model.User;
import com.reactive.microservices.userservice.repository.UsersR2DbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceResponseFactory responseFactory;
    private final UsersR2DbcRepository usersRepository;

    public Flux<User> getAllUsers() {
        return this.usersRepository.findAll()
                .map(responseFactory::toModel);
    }

    public Mono<User> getUser(Integer userId) {
        return this.usersRepository
                .findById(userId)
                .map(responseFactory::toModel);
    }

    public Mono<User> createUser(Mono<User> userMono) {
        return userMono
                .map(responseFactory::toEntity)
                .flatMap(usersRepository::save)
                .map(responseFactory::toModel);
    }

    public Mono<User> updateUser(Integer userId, Mono<User> userMono) {
        return this.usersRepository.findById(userId)
                .flatMap(userEntity -> userMono
                        .map(responseFactory::toEntity)
                        .doOnNext(entity -> entity.setId(userId)))
                .flatMap(usersRepository::save)
                .map(responseFactory::toModel);
    }


    public Mono<Void> deleteUser(Integer userId) {
        return this.usersRepository.deleteById(userId);
    }

}
