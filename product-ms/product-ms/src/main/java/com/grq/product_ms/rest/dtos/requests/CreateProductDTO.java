package com.grq.product_ms.rest.dtos.requests;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    String title;
    BigDecimal price;
    Integer quantity;
}
