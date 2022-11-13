package com.reactive.microservices.orderservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class User {
    private Integer id;
    private String name;
    private Integer balance;
}
