package com.capitole.challenge.starwars.infrastructure.api.controller;

import com.capitole.challenge.starwars.api.lib.controller.StarhipsV1Api;
import com.capitole.challenge.starwars.api.lib.model.StarshipListResponseDto;
import com.capitole.challenge.starwars.application.port.input.GetStarshipsUseCase;
import com.capitole.challenge.starwars.domain.model.Sort;
import com.capitole.challenge.starwars.infrastructure.api.mapper.StarshipDtoMapper;
import com.capitole.challenge.starwars.infrastructure.api.presenter.UseCaseExecutorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarshipController implements StarhipsV1Api, UseCaseExecutorPort {

  private final GetStarshipsUseCase getStarshipsUseCase;
  private final StarshipDtoMapper mapper;

  @Cacheable(cacheNames = {"starship"}, key = "#name+#sortBy+#sortOrder")
  @Override
  public ResponseEntity<StarshipListResponseDto> getStarshipsFilteredAndSorted(String name, String sortBy,
      String sortOrder) {
    return functional(
        GetStarshipsUseCase.InputValues.builder()
            .name(name)
            .sort(Sort.builder().field(sortBy).order(sortOrder).build())
            .build()
        ,
        getStarshipsUseCase,
        output -> ResponseEntity.ok().body(mapper.toDto(output.getStarships()))
    );
  }
}
