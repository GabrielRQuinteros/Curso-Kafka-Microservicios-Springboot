package com.grq.email_notification_msv.configuration.kafka;

import com.grq.email_notification_msv.exceptions.NotRetryableException;
import com.grq.email_notification_msv.exceptions.RetryableException;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.topics.product.product-created.dlt}")
    private String productCreatedTopicDLT;

    // ─── CONSUMER ────────────────────────────────────────────────

    /** Configura un ConsumerFactory que utiliza deserialización tanto en claves como en valores y tambien
     * establece como se deserializaran los errores.
     * Por último también establece que se pueden deserializar objetos de cualquier paquete (JacksonJsonDeserializer.TRUSTED_PACKAGES = "*")
     * @return
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        /// El groupId es fundamental para que el consumer pueda unirse a un grupo y recibir mensajes. Esto hace lo mismo
        /// que definir el groupId en application.properties en KafkaListener donde se declaro que escuchaba el consumer, pero lo hacemos acá para centralizar toda la configuración del consumer en un solo lugar.
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class);

        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    /** Configura un ConcurrentKafkaListenerContainerFactory que utiliza el ConsumerFactory definido anteriormente.
     * Además, establece una concurrencia de 3 para permitir el procesamiento paralelo de mensajes.
     * Osea, se pueden procesar hasta 3 mensajes simultáneamente. 1 Por cada broker que tengo en mi cluster.
     * @param errorHandler
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    // ─── PRODUCER PARA DLT Y OTROS USOS ─────────────────────────────────

    /** Configura un ProducerFactory que utiliza StringSerializer para las claves y JacksonJsonSerializer para los valores.
     * Este producer se usará para enviar mensajes a las DLT en caso de errores.
     * @return
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    // ─── ERROR HANDLER + DLT TOPIC ───────────────────────────────
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        /// Configura un recoverer que envía los mensajes fallidos a una DLT específica basada en el topic original y la partición.
        ///  Me permite por ejemplo ante un mensaje del topic "product-created" enviar el mensaje fallido a "product-created.DLT" manteniendo la partición original.
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, ex) -> new TopicPartition(record.topic() + ".DLT", record.partition())
        );

        /// Configura un error handler con un recoverer que envía los mensajes fallidos a una DLT después de 3 reintentos con un backoff fijo de 5 segundos.
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(5000L, 3));

        /// Configura qué excepciones son retryables y cuáles no. Me da control total sobre el comportamiento de reintentos
        /// y en que situaciones hacer reintentos o enviar a DLT.
        errorHandler.addNotRetryableExceptions(NotRetryableException.class);
        errorHandler.addRetryableExceptions(RetryableException.class);
        return errorHandler;
    }


    /** Crea en caso de que no exista el topic DLT para los mensajes fallidos del topic "product-created". Este topic tendrá 3 particiones y una réplica.
     * 1 replica por cada broker que tengo en mi cluster.
     * @return
     */
    @Bean
    public NewTopic emailNotificationDLT() {
        return TopicBuilder.name(productCreatedTopicDLT)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
