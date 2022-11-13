package com.reactive.microservices.userservice.controller;

import com.reactive.microservices.userservice.model.User;
import com.reactive.microservices.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/get/all")
    public Flux<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/get/{userId}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable(value = "userId") Integer userId) {
        return this.userService.getUser(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Mono<User> createUser(@RequestBody Mono<User> userMono) {
        return this.userService.createUser(userMono);
    }

    @PutMapping("/update/{userId}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable(value = "userId") Integer userId,
                                                 @RequestBody Mono<User> userMono) {
        return this.userService.updateUser(userId, userMono)
                .map(user -> new ResponseEntity<>(user, HttpStatus.CREATED))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public Mono<Void> deleteUser(@PathVariable(value = "userId") Integer userId) {
        return this.userService.deleteUser(userId);
    }
}
