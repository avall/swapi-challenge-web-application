package com.capitole.challenge.infrastructure.rest.client.adapter;

import com.capitole.challenge.application.port.output.GetPeopleOutPort;
import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import java.util.List;
import java.util.Optional;

public class PeopleAdapter implements GetPeopleOutPort {


  @Override
  public Optional<List<People>> getPeople(Optional<String> name, Optional<Sort> sort) {
    return Optional.empty();
  }
}
