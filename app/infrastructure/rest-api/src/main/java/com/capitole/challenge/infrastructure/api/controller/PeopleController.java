package com.capitole.challenge.infrastructure.api.controller;

import com.capitole.challenge.api.lib.controller.PeopleV1Api;
import com.capitole.challenge.api.lib.model.PeopleListResponseDto;
import com.capitole.challenge.infrastructure.api.presenter.UseCaseExecutorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PeopleController implements PeopleV1Api, UseCaseExecutorPort {

  @Override
  public ResponseEntity<PeopleListResponseDto> getPeopleByNameSorted(String name, String sortOrder,
      String sortBy) {
    return null;
  }
}
