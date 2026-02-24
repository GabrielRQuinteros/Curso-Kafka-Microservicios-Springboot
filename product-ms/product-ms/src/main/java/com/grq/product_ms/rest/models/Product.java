package com.grq.product_ms.rest.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
