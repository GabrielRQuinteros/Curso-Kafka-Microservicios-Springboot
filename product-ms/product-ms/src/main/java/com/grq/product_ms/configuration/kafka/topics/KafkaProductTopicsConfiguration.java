package com.grq.product_ms.configuration.kafka.topics;

import com.grq.product_ms.events.topics.product.ProductTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaProductTopicsConfiguration {


    @Bean
    NewTopic createProductTopic() {
        return TopicBuilder.name(ProductTopics.PRODUCT_CREATED)
                .partitions(3)
                .replicas(3)
                /// Asegura que al menos 2 réplicas deben estar en sincronia para que un mensaje sea considerado como enviado con éxito.
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
