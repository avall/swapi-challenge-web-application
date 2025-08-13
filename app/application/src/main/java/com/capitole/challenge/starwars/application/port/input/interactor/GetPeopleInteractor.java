package com.capitole.challenge.starwars.application.port.input.interactor;

import com.capitole.challenge.starwars.application.exception.CoreException;
import com.capitole.challenge.starwars.application.port.input.GetPeopleUseCase;
import com.capitole.challenge.starwars.application.port.output.GetPeopleOutPort;
import com.capitole.challenge.starwars.domain.decorator.Interactor;
import lombok.RequiredArgsConstructor;

/**
 * @author alex.vall
 *
 * Interactor for the get people use case
 */
@Interactor
@RequiredArgsConstructor
public class GetPeopleInteractor implements GetPeopleUseCase {

  private final GetPeopleOutPort outPort;

  @Override
  public OutputValues execute(InputValues input) throws CoreException {
    return OutputValues.builder()
        .people(outPort.getPeople(input.getName(), input.getSort()))
        .build();
  }
}
