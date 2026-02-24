package com.grq.email_notification_msv.consumer;

import com.grq.core.events.ProductCreatedEvent;
import com.grq.email_notification_msv.consumer.constants.ProductEventConstants;
import com.grq.email_notification_msv.rest.services.internal.interfaces.ProductEventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ProductEventService productEventService;
    private final Logger logger = LoggerFactory.getLogger( ProductEventConsumer.class );

    @KafkaListener(
            topics = ProductEventConstants.PRODUCT_CREATED_EVENT_TOPIC,
            groupId = ProductEventConstants.EMAIL_NOTIFICATION_GROUP,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleProductCreatedEvent(
            @Payload ProductCreatedEvent event,
            @Header(value = "messageId", required = false) byte[] messageIdBytes,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String messageKey
    )
    {
        /// Al lanzar la excepcion NotRetryableException, el mensaje no se reintentará y
        /// se enviará directamente al topic de errores dead letter correspondiente.
        //if(true) throw new NotRetryableException("!!!ACA!!! --> Simulando un error no retryable para el evento product-created-event - ProductId: " + event.getId());
        logger.info("!!!ACA!!! --> Evento product-created-event recibido - ProductId: {}", event.getId());
        String messageId;
        String messageKeyFinal;
        if (messageIdBytes != null) {
            messageId = new String(messageIdBytes, StandardCharsets.UTF_8);
        } else {
            messageId = "No llego MessageId";
        }
        productEventService.handleProductCreatedEvent (event, messageId, messageKey);
    }

}
