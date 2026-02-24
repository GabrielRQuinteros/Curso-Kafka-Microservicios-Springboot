package com.grq.product_ms.rest.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductDTO {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
