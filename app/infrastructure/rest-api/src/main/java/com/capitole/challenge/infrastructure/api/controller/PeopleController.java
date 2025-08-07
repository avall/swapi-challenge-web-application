package com.capitole.challenge.infrastructure.api.controller;

import com.capitole.challenge.api.lib.controller.PeopleV1Api;
import com.capitole.challenge.api.lib.model.PeopleListResponseDto;
import com.capitole.challenge.application.port.input.GetPeopleUseCase;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.SortOrder;
import com.capitole.challenge.infrastructure.api.mapper.PeopleDtoMapper;
import com.capitole.challenge.infrastructure.api.presenter.UseCaseExecutorPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PeopleController implements PeopleV1Api, UseCaseExecutorPort {

  private final GetPeopleUseCase getPeopleUseCase;
  private final PeopleDtoMapper mapper;

  @Override
  public ResponseEntity<PeopleListResponseDto> getPeopleByNameSorted(String name, String sortOrder,
      String sortBy) {
    return functional(
            GetPeopleUseCase.InputValues.builder()
                .name(Optional.ofNullable(name))
                .sort(
                    Optional.ofNullable(sortBy)
                        .map(s -> Sort.builder().field(s).order(
                            Optional.ofNullable(sortOrder)
                                .map(SortOrder::valueOf)
                                .orElse(SortOrder.ASC)
                        ).build()))
                .build()
        ,
        getPeopleUseCase,
        output -> ResponseEntity.ok().body(mapper.toDto(output.getPeople()))
    );
  }
}
