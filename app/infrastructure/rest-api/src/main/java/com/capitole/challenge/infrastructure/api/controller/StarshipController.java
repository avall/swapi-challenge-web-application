package com.capitole.challenge.infrastructure.api.controller;

import com.capitole.challenge.api.lib.controller.StarhipsV1Api;
import com.capitole.challenge.api.lib.model.StarshipListResponseDto;
import com.capitole.challenge.application.port.input.GetPeopleUseCase;
import com.capitole.challenge.application.port.input.GetStarshipsUseCase;
import com.capitole.challenge.domain.model.Sort;
import com.capitole.challenge.domain.model.SortOrder;
import com.capitole.challenge.infrastructure.api.mapper.PeopleDtoMapper;
import com.capitole.challenge.infrastructure.api.mapper.StarshipDtoMapper;
import com.capitole.challenge.infrastructure.api.presenter.UseCaseExecutorPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarshipController implements StarhipsV1Api, UseCaseExecutorPort {

  private final GetStarshipsUseCase getStarshipsUseCase;
  private final StarshipDtoMapper mapper;

  @Override
  public ResponseEntity<StarshipListResponseDto> getStarshipsByNameSorted(String name,
      String sortOrder, String sortBy) {
    return functional(
        GetStarshipsUseCase.InputValues.builder()
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
        getStarshipsUseCase,
        output -> ResponseEntity.ok().body(mapper.toDto(output.getStarships()))
    );
  }
}
