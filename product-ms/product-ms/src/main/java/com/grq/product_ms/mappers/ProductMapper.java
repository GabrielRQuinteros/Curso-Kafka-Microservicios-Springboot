package com.grq.product_ms.mappers;

import com.grq.product_ms.rest.dtos.requests.CreateProductDTO;
import com.grq.product_ms.rest.dtos.responses.ProductDTO;
import com.grq.product_ms.rest.models.Product;

import java.util.UUID;

public class ProductMapper {

    public static ProductDTO toProductDTO( Product product){
        return ProductDTO.builder()
                .id(product.getId().toString())
                .title(product.getTitle())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }

    public static Product toProduct( ProductDTO productDTO){
        return Product.builder()
                .id( productDTO.getId() == null ? null : UUID.fromString(productDTO.getId()) )
                .title(productDTO.getTitle())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .build();
    }

    public static Product toProduct( CreateProductDTO createProductDTO){
        return Product.builder()
                .id( null )
                .title(createProductDTO.getTitle())
                .quantity(createProductDTO.getQuantity())
                .price(createProductDTO.getPrice())
                .build();
    }

}
