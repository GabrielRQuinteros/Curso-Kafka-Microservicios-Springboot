package com.grq.product_ms.clients.pokeapi;

import com.grq.product_ms.integrations.pokeapi.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PokeApiClient {

    private final WebClient webClient;

    // Spring inyecta el bean por nombre
    public PokeApiClient(@Qualifier("pokeApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Response> getPokemonByName(String name) {
        return webClient.get()
                .uri("/pokemon/{name}", name)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error PokeAPI: " + body))
                )
                .bodyToMono(Response.class);
    }
}