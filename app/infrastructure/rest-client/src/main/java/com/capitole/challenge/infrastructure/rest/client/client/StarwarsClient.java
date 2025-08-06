package com.capitole.challenge.infrastructure.rest.client.client;

import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.SortOrder;
import com.capitole.challenge.infrastructure.rest.client.dto.StarshipDto;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class StarwarsClient {

  private final WebClient client;


  public Mono<List<People>> getPeople(Optional<String> name, Optional<Sort> sort) {
    return client
        .get()
        .uri("/api/people")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<People>>() {})
        .map(peopleList -> {
          List<People> filteredList = name
              .filter(s -> !s.trim().isEmpty())
              .map(filterName -> peopleList.stream()
                  .filter(person -> person.name() != null && person.name().toLowerCase().contains(filterName.toLowerCase()))
                  .collect(Collectors.toList()))
              .orElse(peopleList);

          // --- CAMBIO AQUÍ: Uso de Optional.ofNullable(comparator).map(...) ---
          return sort
              .map(s -> {
                Comparator<People> comparator = null;
                if ("name".equalsIgnoreCase(s.field())) {
                  comparator = Comparator.comparing(People::name);
                }
                if ("created".equalsIgnoreCase(s.field())) {
                  comparator = Comparator.comparing(People::name);
                }
                return Optional.ofNullable(comparator)
                    .map(comp -> {
                      if (s.order() == SortOrder.DESC) {
                        comp = comp.reversed();
                      }
                      return filteredList.stream()
                          .sorted(comp)
                          .collect(Collectors.toList());
                    })
                    .orElse(filteredList); // Si el comparador es null, devuelve la lista filtrada sin ordenar
              })
              .orElse(filteredList);
        });
  }


  public Mono<List<StarshipDto>> getStarships(Optional<String> name, Optional<Sort> sort) {
    return client
        .get()
        .uri("/api/people")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<StarshipDto>>() {})
        .map(peopleList -> {
          List<StarshipDto> filteredList = name
              .filter(s -> !s.trim().isEmpty())
              .map(filterName -> peopleList.stream()
                  .filter(person -> person.getName() != null && person.getName().toLowerCase().contains(filterName.toLowerCase()))
                  .collect(Collectors.toList()))
              .orElse(peopleList);

          // --- CAMBIO AQUÍ: Uso de Optional.ofNullable(comparator).map(...) ---
          return sort
              .map(s -> {
                Comparator<StarshipDto> comparator = null;
                if ("name".equalsIgnoreCase(s.field())) {
                  comparator = Comparator.comparing(StarshipDto::getName);
                }
                if ("created".equalsIgnoreCase(s.field())) {
                  comparator = Comparator.comparing(StarshipDto::getCreated);
                }
                return Optional.ofNullable(comparator)
                    .map(comp -> {
                      if (s.order() == SortOrder.DESC) {
                        comp = comp.reversed();
                      }
                      return filteredList.stream()
                          .sorted(comp)
                          .collect(Collectors.toList());
                    })
                    .orElse(filteredList); // Si el comparador es null, devuelve la lista filtrada sin ordenar
              })
              .orElse(filteredList);
        });
  }

}

