package com.capitole.challenge.infrastructure.rest.client;

import com.capitole.challenge.infrastructure.rest.client.dto.PeopleDto;
import com.capitole.challenge.infrastructure.rest.client.dto.StarshipDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class StarwarsClient {

  private final WebClient starwars;

  public Mono<List<PeopleDto>> getAllPeople() {
    return starwars
        .get()
        .uri("/api/people")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<PeopleDto>>() {});
  }

  public Mono<List<StarshipDto>> getAllStarships() {
    return starwars
        .get()
        .uri("/api/starships")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<StarshipDto>>() {});
  }
}
