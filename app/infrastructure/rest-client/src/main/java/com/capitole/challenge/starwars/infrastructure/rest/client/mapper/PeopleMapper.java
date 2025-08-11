package com.capitole.challenge.starwars.infrastructure.rest.client.mapper;

import com.capitole.challenge.starwars.domain.model.People;
import com.capitole.challenge.starwars.infrastructure.rest.client.dto.PeopleDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author alex.vall
 *
 * @since 1.0.0
 * Mapper used to map the People entity from swapi.info
 */
@Mapper(componentModel = "spring")
public interface PeopleMapper {

  PeopleDto toDto(People entity);

  People toDomain(PeopleDto entity);

  List<PeopleDto> toDto(List<People> entity);

  List<People> toDomain(List<PeopleDto> entity);

}
