package com.capitole.challenge.starwars.application.port.input;

import com.capitole.challenge.starwars.application.port.UseCase;
import com.capitole.challenge.starwars.domain.model.Sort;
import com.capitole.challenge.starwars.domain.model.Starship;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Interface definition for the se case
 */
public interface GetStarshipsUseCase
    extends UseCase<GetStarshipsUseCase.InputValues,
    GetStarshipsUseCase.OutputValues> {

  /**
   * Input parameters as input to the Use Case
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  class InputValues implements UseCase.InputValues {
    private String name;
    private Sort sort;
  }

  /**
   * Output parameters returned from the Use Case
   */
  @Data
  @Builder
  class OutputValues implements UseCase.OutputValues {
    private List<Starship> starships;
  }
}
