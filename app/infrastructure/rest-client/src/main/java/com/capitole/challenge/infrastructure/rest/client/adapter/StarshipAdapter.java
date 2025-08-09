package com.capitole.challenge.infrastructure.rest.client.adapter;

import com.capitole.challenge.application.port.output.GetStarshipOutPort;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.Starship;
import com.capitole.challenge.infrastructure.rest.client.client.StarwarsClient;
import com.capitole.challenge.infrastructure.rest.client.mapper.StarshipMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StarshipAdapter implements GetStarshipOutPort {

  private final StarwarsClient client;
  private final StarshipMapper mapper;

  @Override
  public List<Starship> getStarships(String name, Sort sort) {
    return client.getStarships(name, sort).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
