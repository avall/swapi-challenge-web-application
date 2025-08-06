package com.capitole.challenge.application.port.output;

import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.Starship;
import java.util.List;
import java.util.Optional;

public interface GetStarshipOutPort {
    Optional<List<Starship>> getStarships(Optional<String> name, Optional<Sort> sort);
}
