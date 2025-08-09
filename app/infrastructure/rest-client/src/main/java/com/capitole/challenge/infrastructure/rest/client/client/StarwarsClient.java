package com.capitole.challenge.infrastructure.rest.client.client;

import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.SortOrder;
import com.capitole.challenge.infrastructure.rest.client.dto.PeopleDto;
import com.capitole.challenge.infrastructure.rest.client.dto.StarshipDto;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class StarwarsClient {

  private final WebClient starwarsWebClient;

  public List<PeopleDto> getPeople(String name, Sort sort) {
    List<PeopleDto> peopleList = starwarsWebClient
        .get()
        .uri("/api/people")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<PeopleDto>>() {})
        .block();

    List<PeopleDto> filteredList = Optional.ofNullable(name)
          .filter(s -> !s.trim().isEmpty())
          .map(filterName -> peopleList.stream()
              .filter(person -> person.name() != null && person.name().toLowerCase().contains(filterName.toLowerCase()))
              .toList())
          .orElse(peopleList);

          Comparator<PeopleDto> comparator = null;
          if ("name".equalsIgnoreCase(sort.field())) {
            comparator = Comparator.comparing(PeopleDto::name);
          }
          if ("created".equalsIgnoreCase(sort.field())) {
            comparator = Comparator.comparing(PeopleDto::name);
          }
    return Optional.ofNullable(comparator)
              .map(comp -> {
                if (sort.order() == SortOrder.DESC) {
                  comp = comp.reversed();
                }
                return filteredList.stream()
                    .sorted(comp)
                    .toList();
              })
              .orElse(filteredList);
  }


  public List<StarshipDto> getStarships(String name, Sort sort) {
    List<StarshipDto> starshipList = starwarsWebClient
        .get()
        .uri("/api/starships")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<StarshipDto>>() {})
        .block();

    List<StarshipDto> filteredList = Optional.ofNullable(name)
        .filter(s -> !s.trim().isEmpty())
        .map(filterName -> starshipList.stream()
            .filter(person -> person.name() != null && person.name().toLowerCase().contains(filterName.toLowerCase()))
            .toList())
        .orElse(starshipList);

    Comparator<StarshipDto> comparator = null;
    if ("name".equalsIgnoreCase(sort.field())) {
      comparator = Comparator.comparing(StarshipDto::name);
    }
    if ("created".equalsIgnoreCase(sort.field())) {
      comparator = Comparator.comparing(StarshipDto::name);
    }

    return Optional.ofNullable(comparator)
        .map(comp -> {
          if (sort.order() == SortOrder.DESC) {
            comp = comp.reversed();
          }
          return filteredList.stream()
              .sorted(comp)
              .toList();
        })
        .orElse(filteredList);
  }
}

