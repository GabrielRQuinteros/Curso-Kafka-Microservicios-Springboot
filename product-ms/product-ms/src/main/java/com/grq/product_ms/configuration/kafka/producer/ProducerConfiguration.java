package com.grq.product_ms.configuration.kafka.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {


    /* ================= BOOTSTRAP SERVERS ================= */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /* ================= TOPICS ================= */
    @Value("${kafka.topics.product.product-created}")
    private String productCreatedTopic;

    /* ================= ACKS ================= */
    @Value("${spring.kafka.producer.acks}")
    private String acks;

    /* ================= RETRIES ================= */
    @Value("${spring.kafka.producer.retries}")
    private String retries;

    @Value("${spring.kafka.producer.retry-backoff-ms}")
    private String retryBackoffMs;

    /* ================= TIMEOUTS ================= */
    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeoutMs;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private String requestTimeoutMs;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    private String lingerMs;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private boolean idempotence;




    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        props.put(ProducerConfig.ACKS_CONFIG, acks);

        props.put(ProducerConfig.RETRIES_CONFIG, Integer.parseInt(retries));
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, Integer.parseInt(retryBackoffMs));

        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, Integer.parseInt(deliveryTimeoutMs));
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, Integer.parseInt(requestTimeoutMs));
        props.put(ProducerConfig.LINGER_MS_CONFIG, Integer.parseInt(lingerMs));

        //ACTIVAMOS IDEMPOTENCIA
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);


        return props;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }


}
