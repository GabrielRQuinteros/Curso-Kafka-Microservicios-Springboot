package com.grq.product_ms.rest.services.internal.implementations;

import com.grq.core.events.ProductCreatedEvent;
import com.grq.product_ms.events.producers.ProductEventProducer;
import com.grq.product_ms.exceptions.ProducerException;
import com.grq.product_ms.mappers.ProductMapper;
import com.grq.product_ms.rest.dtos.requests.CreateProductDTO;
import com.grq.product_ms.rest.models.Product;
import com.grq.product_ms.rest.repositories.ProductRepository;
import com.grq.product_ms.rest.services.internal.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductEventProducer productEventProducer;
    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Transactional( readOnly = false )
    @Override
    public Product createProduct(CreateProductDTO createProductDTO) throws ProducerException {
        Product product = ProductMapper.toProduct( createProductDTO );
        product = this.productRepository.save( product );
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                                                        .id(product.getId().toString())
                                                        .title(createProductDTO.getTitle())
                                                        .price(createProductDTO.getPrice())
                                                        .quantity( createProductDTO.getQuantity() )
                                                        .build();
        String key = product.getId().toString();
    //    productEventProducer.sendProductCreated( key, event );
       /* for (int i = 0; i < 30; i++) {
            event.setQuantity( event.getQuantity() + i );
            event.setTitle(event.getTitle() + i );
            // OPCIÓN 1: Al usar la misma clave, los eventos se ordenarán por clave.
            // Al tener el mismo key --> Todos los eventos se ordenan sobre la misma partición
            // Al ordenarse sobre la misma partición, solo un consumer va a consumir los eventos.
            // productEventProducer.sendProductCreated( key , event );

            // OPCION 2: Al usar claves diferentes, los eventos se distribuirán entre las particiones de manera round-robin.
            // Al tener claves diferentes --> Los eventos se distribuyen entre las particiones y todos los consumers del grupo pueden consumir eventos.
            productEventProducer.sendProductCreated( key + i , event );
        }*/
        productEventProducer.sendProductCreated( key , event );
        return product;
    }
}
