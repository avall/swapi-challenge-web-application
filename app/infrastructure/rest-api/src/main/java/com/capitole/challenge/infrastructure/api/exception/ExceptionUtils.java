package com.capitole.challenge.infrastructure.api.exception;

import com.capitole.challenge.api.lib.model.ErrorDetailsInnerDto;
import com.capitole.challenge.api.lib.model.ErrorDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utils class used for building {@link ErrorDto} from exceptions.
 *
 * @since 1.0.0
 */
public class ExceptionUtils {

  private ExceptionUtils() {}

  /**
   * Builds {@link List} of {@link ErrorDetailsInnerDto} from a {@link Map} of {@link String}.
   *
   * @param invalidDetails the map that will be used for building the output
   * @return {@link List} of {@link ErrorDetailsInnerDto}
   */
  public static List<ErrorDetailsInnerDto> buildInvalidDetails(Map<String, String> invalidDetails) {
    if (invalidDetails == null) {
      return List.of();
    }
    var result = new ArrayList<ErrorDetailsInnerDto>();
    for (var entry : invalidDetails.entrySet()) {
      result.add(ErrorDetailsInnerDto.builder()
          .field(entry.getKey())
          .value(entry.getValue())
          .build());
    }
    return result;
  }
}
