package com.reactive.microservices.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor(staticName = "create")
public class ProductEntity {

    @Id
    private String id;
    private String description;
    private Integer price;
}
