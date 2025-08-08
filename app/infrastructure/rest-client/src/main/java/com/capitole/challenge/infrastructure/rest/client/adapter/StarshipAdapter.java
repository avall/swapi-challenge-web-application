package com.capitole.challenge.infrastructure.rest.client.adapter;

import com.capitole.challenge.application.port.output.GetStarshipOutPort;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.Starship;
import com.capitole.challenge.infrastructure.rest.client.client.StarwarsClient;
import com.capitole.challenge.infrastructure.rest.client.mapper.StarshipMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StarshipAdapter implements GetStarshipOutPort {

  private final StarwarsClient client;
  private final StarshipMapper mapper;

  @Cacheable("Starship")
  @Override
  public List<Starship> getStarships(Optional<String> name, Optional<Sort> sort) {
    return Objects.requireNonNull(client.getStarships(name, sort).block()).stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
  }
}
