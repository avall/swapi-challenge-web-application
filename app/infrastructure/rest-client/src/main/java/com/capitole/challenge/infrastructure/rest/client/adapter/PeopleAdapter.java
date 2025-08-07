package com.capitole.challenge.infrastructure.rest.client.adapter;

import com.capitole.challenge.application.port.output.GetPeopleOutPort;
import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.infrastructure.rest.client.client.StarwarsClient;
import com.capitole.challenge.infrastructure.rest.client.mapper.PeopleMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PeopleAdapter implements GetPeopleOutPort {

  private final StarwarsClient client;
  private final PeopleMapper mapper;

  @Override
  public List<People> getPeople(Optional<String> name, Optional<Sort> sort) {
    return Objects.requireNonNull(client.getPeople(name, sort).block()).stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
  }
}
