package com.grq.product_ms.rest.controllers;


import com.grq.product_ms.exceptions.ProducerException;
import com.grq.product_ms.mappers.ProductMapper;
import com.grq.product_ms.rest.dtos.requests.CreateProductDTO;
import com.grq.product_ms.rest.dtos.responses.ProductDTO;
import com.grq.product_ms.rest.models.Product;
import com.grq.product_ms.rest.services.internal.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    ResponseEntity<ProductDTO> createProduct( @Valid @RequestBody CreateProductDTO createProductDTO ) throws ProducerException {

        Product createdProduct = this.productService.createProduct(createProductDTO);


        return ResponseEntity.status(HttpStatus.CREATED.value())
                             .body( ProductMapper.toProductDTO(createdProduct) );
    }

}
