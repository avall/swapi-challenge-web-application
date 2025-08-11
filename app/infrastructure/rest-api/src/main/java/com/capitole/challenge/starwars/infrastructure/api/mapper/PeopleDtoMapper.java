package com.capitole.challenge.starwars.infrastructure.api.mapper;

import com.capitole.challenge.starwars.api.lib.model.PeopleDto;
import com.capitole.challenge.starwars.api.lib.model.PeopleListResponseDto;
import com.capitole.challenge.starwars.domain.model.People;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author alex.vall
 *
 * @since 1.0.0
 * Mapper used to map the People entity from swapi.info
 */
@Mapper(componentModel = "spring")
public interface PeopleDtoMapper {

  @Mapping(target = "created", source = "created", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
  @Mapping(target = "edited", source = "edited", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
  PeopleDto toDto(People entity);

  default PeopleListResponseDto toDto(List<People> entityList) {
    return PeopleListResponseDto.builder()
        .content(
            Optional.ofNullable(entityList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::toDto)
                .toList()
        ).build();
  }
}
