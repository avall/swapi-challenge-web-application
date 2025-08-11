package com.capitole.challenge.starwars.application.port.output;

import com.capitole.challenge.starwars.domain.model.People;
import com.capitole.challenge.starwars.domain.model.Sort;
import java.util.List;

public interface GetPeopleOutPort {
    List<People> getPeople(String name, Sort sort);
}
