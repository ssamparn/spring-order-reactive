package com.reactive.microservices.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {
    private String id;
    private String description;
    private Integer price;
}
