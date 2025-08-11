package com.capitole.challenge.starwars.infrastructure.rest.client.mapper;

import com.capitole.challenge.starwars.domain.model.People;
import com.capitole.challenge.starwars.infrastructure.rest.client.dto.PeopleDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

  PeopleDto toDto(People entity);

  People toDomain(PeopleDto entity);

  List<PeopleDto> toDto(List<People> entity);

  List<People> toDomain(List<PeopleDto> entity);

}
