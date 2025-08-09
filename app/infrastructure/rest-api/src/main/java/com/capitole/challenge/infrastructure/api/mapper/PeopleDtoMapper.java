package com.capitole.challenge.infrastructure.api.mapper;

import com.capitole.challenge.api.lib.model.PeopleDto;
import com.capitole.challenge.api.lib.model.PeopleListResponseDto;
import com.capitole.challenge.domain.model.People;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
