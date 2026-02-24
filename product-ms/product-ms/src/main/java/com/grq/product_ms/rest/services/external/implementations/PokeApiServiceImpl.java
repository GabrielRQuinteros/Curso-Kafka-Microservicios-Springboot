package com.grq.product_ms.rest.services.external.implementations;

import com.grq.product_ms.clients.pokeapi.PokeApiClient;
import com.grq.product_ms.integrations.pokeapi.Response;
import com.grq.product_ms.rest.services.external.interfaces.PokeApiService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokeApiServiceImpl implements PokeApiService {

    private final PokeApiClient pokeApiClient;

    public PokeApiServiceImpl(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public Mono<Response> obtenerPokemon(String name) {
        return pokeApiClient.getPokemonByName(name);
    }
}
