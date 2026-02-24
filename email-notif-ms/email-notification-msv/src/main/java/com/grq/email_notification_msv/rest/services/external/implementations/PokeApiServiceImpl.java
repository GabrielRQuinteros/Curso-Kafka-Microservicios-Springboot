package com.grq.email_notification_msv.rest.services.external.implementations;

import com.grq.email_notification_msv.clients.PokeApiClient;
import com.grq.email_notification_msv.pokeapi.PokeApiResponse;
import com.grq.email_notification_msv.rest.services.external.interfaces.PokeApiService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokeApiServiceImpl implements PokeApiService {

    private final PokeApiClient pokeApiClient;

    public PokeApiServiceImpl(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public Mono<PokeApiResponse> getPokemonByName(String name) {
        return pokeApiClient.getPokemonByName(name);
    }
}
