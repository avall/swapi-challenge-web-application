package com.capitole.challenge.starwars.application.port.input.interactor;

import com.capitole.challenge.starwars.application.exception.CoreException;
import com.capitole.challenge.starwars.application.port.input.GetStarshipsUseCase;
import com.capitole.challenge.starwars.application.port.output.GetStarshipOutPort;
import com.capitole.challenge.starwars.domain.decorator.Interactor;
import lombok.RequiredArgsConstructor;

/**
 * @author alex.vall
 *
 * Interactor for the Get Starships use case
 */
@Interactor
@RequiredArgsConstructor
public class GetStarshipsInteractor implements GetStarshipsUseCase {

  private final GetStarshipOutPort outPort;

  @Override
  public OutputValues execute(InputValues input) throws CoreException {
    return OutputValues.builder()
        .starships(outPort.getStarships(input.getName(), input.getSort()))
        .build();
  }
}
