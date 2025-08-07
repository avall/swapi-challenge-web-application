package com.capitole.challenge.infrastructure.rest.client.mapper;

import com.capitole.challenge.domain.model.Starship;
import com.capitole.challenge.infrastructure.rest.client.dto.StarshipDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StarshipMapper {

  StarshipDto toDto(Starship entity);

  Starship toDomain(StarshipDto entity);

  List<StarshipDto> toDto(List<Starship> entity);

  List<Starship> toDomain(List<StarshipDto> entity);

}
