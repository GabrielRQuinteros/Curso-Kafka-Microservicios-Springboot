package com.grq.email_notification_msv.rest.services.internal.implementations;

import com.grq.core.events.ProductCreatedEvent;
import com.grq.email_notification_msv.exceptions.NotRetryableException;
import com.grq.email_notification_msv.exceptions.RetryableException;
import com.grq.email_notification_msv.pokeapi.PokeApiResponse;
import com.grq.email_notification_msv.rest.models.events.ProcessedEvent;
import com.grq.email_notification_msv.rest.services.external.interfaces.PokeApiService;
import com.grq.email_notification_msv.rest.services.internal.interfaces.ProcessedEventService;
import com.grq.email_notification_msv.rest.services.internal.interfaces.ProductEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;

@Service
public class ProductEventServiceImpl implements ProductEventService {

    private final Logger logger = LoggerFactory.getLogger(ProductEventServiceImpl.class);

    @Autowired
    private PokeApiService pokeApiService;

    @Autowired
    private ProcessedEventService processedEventService;

    @Transactional( readOnly = false )
    @Override
    public void handleProductCreatedEvent(ProductCreatedEvent event, String messageId, String key ) {

        if( this.processedEventService.isEventProcessed(messageId) )
            throw new NotRetryableException("El evento con id " + messageId + " ya ha sido procesado.");

        try {
        PokeApiResponse pokeApiResponse = pokeApiService.getPokemonByName("pikachu").block(Duration.ofSeconds(5));
        logger.info("!!!ACA!!! --> Respuesta de PokeApi para Pikachu: Name: {}, URL: {}, Altura: {}, Peso: {}", pokeApiResponse.getName(), pokeApiResponse.getSprites().getFrontDefault(),
                pokeApiResponse.getHeight(), pokeApiResponse.getWeight());
        logger.info( "!!!ACA!!! --> Procesando el el servicio el evento product-created-event - ProductId: {}, Titulo: {}, Price: {}, Quantity: {}",
                event.getId(), event.getTitle(), event.getPrice(), event.getQuantity());

        ProcessedEvent processedEvent = new ProcessedEvent( messageId, key );
        this.processedEventService.storeProcessesdEvent( processedEvent );

        } catch (ResourceAccessException e) {
            logger.error("!!ACA!!! --> Error al tratar de acceder a recursos de PokeApi", e.getMessage());
            throw new RetryableException("Error al tratar de acceder a recursos de PokeApi",e);
        }
        catch ( HttpServerErrorException e) {
            logger.error("!!ACA!!! --> Error del servidor de PokeApi: {}", e.getMessage());
            throw new NotRetryableException("Error del servidor de PokeApi", e);
        }
        catch (Exception e) {
            logger.error("!!ACA!!! --> Error al obtener datos de PokeApi: {}", e.getMessage());
            throw new NotRetryableException("Error desconocido al obtener datos de PokeApi.", e);
        }

    }
}
