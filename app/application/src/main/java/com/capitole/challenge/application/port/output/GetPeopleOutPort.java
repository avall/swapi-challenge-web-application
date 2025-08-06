package com.capitole.challenge.application.port.output;

import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import java.util.List;
import java.util.Optional;

public interface GetPeopleOutPort {
    Optional<List<People>> getPeople(Optional<String> name, Optional<Sort> sort);
}
