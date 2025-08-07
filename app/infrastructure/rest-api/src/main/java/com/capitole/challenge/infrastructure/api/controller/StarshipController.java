package com.capitole.challenge.infrastructure.api.controller;

import com.capitole.challenge.api.lib.controller.StarhipsV1Api;
import com.capitole.challenge.api.lib.model.StarshipListResponseDto;
import com.capitole.challenge.infrastructure.api.presenter.UseCaseExecutorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarshipController implements StarhipsV1Api, UseCaseExecutorPort {

  @Override
  public ResponseEntity<StarshipListResponseDto> getStarshipsByNameSorted(String name,
      String sortOrder, String sortBy) {
    return null;
  }
}
