package com.grq.product_ms.events.producers;


import com.grq.core.events.ProductCreatedEvent;
import com.grq.product_ms.events.topics.product.ProductTopics;
import com.grq.product_ms.exceptions.ProducerException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductEventProducer {

    private final KafkaTemplate< String, Object> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    public ProductEventProducer( KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /// Ejemplo de ejecucion sincrona
/*    public void sendProductCreated( String key, ProductCreatedEvent event) {
        CompletableFuture<SendResult<String, ProductCreatedEvent> > completableFuture = kafkaTemplate.send(ProductTopics.PRODUCT_CREATED, key, event);
        completableFuture.whenComplete( ( result, exception ) -> {
            if ( exception == null ) {
                logger.info("******* Evento ProductCreatedEvent enviado exitosamente: *****\n {}", result.getRecordMetadata() );
            } else {
                logger.error( "Error al enviar el evento ProductCreatedEvent:\n {}", exception.getMessage() );
            }
        } );

        /// La siguiente línea hace que la ejecucion pase a ser sincrona,
        /// es decir, espera a que kafka confirme que el mensaje fue aceptado.
        ///  Es similar a usar join() cuando hice un fork() en C.
        completableFuture.join();
    }*/

    public void sendProductCreated( String key, ProductCreatedEvent event) throws ProducerException{
        try {
            // Creamos el ProducerRecord manualmente para tener acceso a toda la metadata del mensaje enviado.
            // Esto me permite agregar headers como el id del mensaje para agregar idempotencia del lado del consumidor.
            ProducerRecord<String, Object> record = new ProducerRecord<>( ProductTopics.PRODUCT_CREATED, key, event );
            // Agregamos un header con un id único para cada mensaje.
            record.headers().add("messageId", UUID.randomUUID().toString().getBytes() );

            // EJEMPLO PARA FORZAR LA REPETICION DEL MENSAJE EN EL CONSUMIDOR, USANDO UN ID FIJO EN EL HEADER.
            //record.headers().add("messageId", "ABC".getBytes() );

            //SendResult<String, Object> result = kafkaTemplate.send(ProductTopics.PRODUCT_CREATED, key, event).get();
            SendResult<String, Object> result = kafkaTemplate.send(record).get();

            logger.info("******* Evento ProductCreatedEvent enviado exitosamente: *****\n {}", result.getRecordMetadata() );
            // Ahora logueamos la particion, el topic y el offser del mensaje enviado, lo cual es muy util para depurar y monitorear el sistema.
            logger.info(
                    """
                    TOPIC: {}
                    PARTITION: {}
                    OFFSET: {}
                    ********************************************
                    """,
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
            return;
        } catch (Exception exception) {
            logger.error( "Error al enviar el evento ProductCreatedEvent:\n {}", exception.getMessage() );
            throw new ProducerException( "Error al enviar el evento ProductCreatedEvent: " + exception.getMessage(), exception );
        }
    }


    public void sendProductCreated( ProductCreatedEvent event) {
        CompletableFuture<SendResult<String, Object> > completableFuture = kafkaTemplate.send(ProductTopics.PRODUCT_CREATED, event);
        completableFuture.whenComplete( ( result, exception ) -> {
            if ( exception == null ) {
                logger.info("******* Evento ProductCreatedEvent enviado exitosamente: *****\n {}", result.getRecordMetadata() );
            } else {
                logger.error( "Error al enviar el evento ProductCreatedEvent:\n {}", exception.getMessage() );
            }
        } );
    }

}
