package com.capitole.challenge.application.port.output;

import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import java.util.List;

public interface GetPeopleOutPort {
    List<People> getPeople(String name, Sort sort);
}
