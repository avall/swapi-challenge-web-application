package com.capitole.challenge.application.port.input.interactor;

import com.capitole.challenge.application.exception.CoreException;
import com.capitole.challenge.application.port.input.GetPeopleUseCase;
import com.capitole.challenge.application.port.output.GetPeopleOutPort;
import com.capitole.challenge.domain.decorator.Interactor;
import lombok.RequiredArgsConstructor;

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
