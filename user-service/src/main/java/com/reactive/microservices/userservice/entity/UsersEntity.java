package com.reactive.microservices.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
@AllArgsConstructor(staticName = "create")
public class UsersEntity {

    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
