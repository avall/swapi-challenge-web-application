package com.capitole.challenge.starwars.infrastructure.rest.client.mapper;

import com.capitole.challenge.starwars.domain.model.Starship;
import com.capitole.challenge.starwars.infrastructure.rest.client.dto.StarshipDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author alex.vall
 *
 * @since 1.0.0
 * Mapper used to map the Starship entity from swapi.info
 */

@Mapper(componentModel = "spring")
public interface StarshipMapper {

  StarshipDto toDto(Starship entity);

  Starship toDomain(StarshipDto entity);

  List<StarshipDto> toDto(List<Starship> entity);

  List<Starship> toDomain(List<StarshipDto> entity);

}
