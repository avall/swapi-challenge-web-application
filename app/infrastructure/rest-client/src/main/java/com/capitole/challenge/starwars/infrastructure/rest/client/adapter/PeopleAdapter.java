package com.capitole.challenge.starwars.infrastructure.rest.client.adapter;

import com.capitole.challenge.starwars.application.port.output.GetPeopleOutPort;
import com.capitole.challenge.starwars.domain.model.People;
import com.capitole.challenge.starwars.domain.model.Sort;
import com.capitole.challenge.starwars.infrastructure.rest.client.client.StarwarsClient;
import com.capitole.challenge.starwars.infrastructure.rest.client.mapper.PeopleMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author alex.vall
 *
 * @since 1.0.0
 * Adapter class used to callout swap.info applying filtering and sorting
 */
@Service
@RequiredArgsConstructor
public class PeopleAdapter implements GetPeopleOutPort {

  private final StarwarsClient client;
  private final PeopleMapper mapper;

  @Override
  public List<People> getPeople(String filterName, Sort sort) {
    return client.getPeople().stream()
        .filter(
            person ->
               Optional.ofNullable(filterName).map(s -> !s.trim().isEmpty() && person.name().toLowerCase().contains(filterName.toLowerCase()))
                  .orElse(true)
        )
        .map(mapper::toDomain)
        .sorted(sort.getComparator())
        .toList();
 }
}
