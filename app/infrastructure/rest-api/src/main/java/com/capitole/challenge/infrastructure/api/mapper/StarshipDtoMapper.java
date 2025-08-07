package com.capitole.challenge.infrastructure.api.mapper;

import com.capitole.challenge.api.lib.model.StarshipDto;
import com.capitole.challenge.api.lib.model.StarshipListResponseDto;
import com.capitole.challenge.domain.model.Starship;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StarshipDtoMapper {

  @Mapping(target = "MGLT", source = "mglt")
  StarshipDto toDto(Starship entity);

  default StarshipListResponseDto toDto(List<Starship> entityList) {
    return StarshipListResponseDto.builder()
        .content(
            Optional.ofNullable(entityList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::toDto)
                .toList()
        ).build();
  }
}
