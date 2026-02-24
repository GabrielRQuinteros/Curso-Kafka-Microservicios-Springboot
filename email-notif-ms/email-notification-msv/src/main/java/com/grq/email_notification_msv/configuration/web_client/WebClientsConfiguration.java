package com.grq.email_notification_msv.configuration.web_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientsConfiguration {

    @Bean
    public WebClient pokeApiWebClient(
            @Value("${apis.externas.pokeapi.base.url}") String baseUrl) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024) // 16MB
                )
                .build();

        return WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(strategies)
                .build();
    }
}

