package com.grq.email_notification_msv.rest.services.external.interfaces;

import com.grq.email_notification_msv.pokeapi.PokeApiResponse;
import reactor.core.publisher.Mono;

public interface PokeApiService {

    public Mono<PokeApiResponse> getPokemonByName(String name) throws RuntimeException;
}
