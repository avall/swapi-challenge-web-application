package com.capitole.challenge.starwars.application.port.output;

import com.capitole.challenge.starwars.domain.model.Sort;
import com.capitole.challenge.starwars.domain.model.Starship;
import java.util.List;

/**
 * @author alex.vall
 *
 * Interface definition for the Get Starships use case
 */
public interface GetStarshipOutPort {
    List<Starship> getStarships(String name, Sort sort);
}
