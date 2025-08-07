package com.capitole.challenge.application.port.input.interactor;

import com.capitole.challenge.application.exception.CoreException;
import com.capitole.challenge.application.port.input.GetStarshipsUseCase;
import com.capitole.challenge.application.port.output.GetStarshipOutPort;
import com.capitole.challenge.domain.decorator.Interactor;
import lombok.RequiredArgsConstructor;

@Interactor
@RequiredArgsConstructor
public class GetStarshipInteractor implements GetStarshipsUseCase {

  private final GetStarshipOutPort outPort;

  @Override
  public OutputValues execute(InputValues input) throws CoreException {
    return OutputValues.builder()
        .starships(outPort.getStarships(input.getName(), input.getSort()))
        .build();
  }
}
