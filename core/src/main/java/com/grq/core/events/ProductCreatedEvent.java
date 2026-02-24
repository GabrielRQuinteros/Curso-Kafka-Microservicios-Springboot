package com.grq.core.events;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreatedEvent {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
