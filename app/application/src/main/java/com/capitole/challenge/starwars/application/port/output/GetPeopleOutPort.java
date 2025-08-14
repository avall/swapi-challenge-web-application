package com.capitole.challenge.starwars.application.port.output;

import com.capitole.challenge.starwars.domain.model.People;
import com.capitole.challenge.starwars.domain.model.Sort;
import java.util.List;

/**
 * @author alex.vall
 *
 * Interface definition for the Get People output port
 */
public interface GetPeopleOutPort {
    List<People> getPeople(String name, Sort sort);
}
