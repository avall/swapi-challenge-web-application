package com.capitole.challenge.application.port.input;

import com.capitole.challenge.application.port.UseCase;
import com.capitole.challenge.domain.model.People;
import com.capitole.challenge.domain.model.Sort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Interface definition for the se case
 */
public interface GetPeopleUseCase
    extends UseCase<GetPeopleUseCase.InputValues,
    GetPeopleUseCase.OutputValues> {

  /**
   * Input parameters as input to the Use Case
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  class InputValues implements UseCase.InputValues {
    private Optional<String> name;
    private Optional<Sort> sort;
  }

  /**
   * Output parameters returned from the Use Case
   */
  @Data
  @Builder
  class OutputValues implements UseCase.OutputValues {
    private List<People> price;
  }
}
