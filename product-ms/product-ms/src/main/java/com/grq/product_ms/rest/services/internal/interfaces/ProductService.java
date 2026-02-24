package com.grq.product_ms.rest.services.internal.interfaces;


import com.grq.product_ms.exceptions.ProducerException;
import com.grq.product_ms.rest.dtos.requests.CreateProductDTO;
import com.grq.product_ms.rest.models.Product;

public interface ProductService {

    Product createProduct(CreateProductDTO createProductDTO ) throws ProducerException;

}
