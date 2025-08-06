package com.capitole.challenge.infrastructure.rest.client.adapter;

import com.capitole.challenge.application.port.output.GetStarshipOutPort;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.Starship;
import java.util.List;
import java.util.Optional;

public class StarshipAdapter implements GetStarshipOutPort {


  @Override
  public Optional<List<Starship>> getStarships(Optional<String> name, Optional<Sort> sort) {
    return Optional.empty();
  }
}
