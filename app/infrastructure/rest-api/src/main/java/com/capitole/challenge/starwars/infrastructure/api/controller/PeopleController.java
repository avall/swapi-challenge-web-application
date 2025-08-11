package com.capitole.challenge.starwars.infrastructure.api.controller;

import com.capitole.challenge.starwars.api.lib.controller.PeopleV1Api;
import com.capitole.challenge.starwars.api.lib.model.PeopleListResponseDto;
import com.capitole.challenge.starwars.application.port.input.GetPeopleUseCase;
import com.capitole.challenge.starwars.domain.model.Sort;
import com.capitole.challenge.starwars.infrastructure.api.mapper.PeopleDtoMapper;
import com.capitole.challenge.starwars.infrastructure.api.presenter.UseCaseExecutorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PeopleController implements PeopleV1Api, UseCaseExecutorPort {

  private final GetPeopleUseCase getPeopleUseCase;
  private final PeopleDtoMapper mapper;

  @Cacheable(cacheNames = {"people"}, key = "#name+#sortBy+#sortOrder")
  @Override
  public ResponseEntity<PeopleListResponseDto> getPeopleFilteredAndSorted(String name, String sortBy,
      String sortOrder) {
    return functional(
            GetPeopleUseCase.InputValues.builder()
                .name(name)
                .sort(Sort.builder().field(sortBy).order(sortOrder).build())
                .build()
        ,
        getPeopleUseCase,
        output -> ResponseEntity.ok().body(mapper.toDto(output.getPeople()))
    );
  }
}

