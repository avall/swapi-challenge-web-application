package com.capitole.challenge.starwars.application.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author alex.vall
*
* Core exception to be used by all the application layers.
*/
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CoreException extends RuntimeException {

  /**
   * Exception message
   */
  private final String message;

  /**
   * Exception unique code
   */
  private final String code;

  /**
   * Exception invalid parameters
   */
  private final Map<String, String> invalidDetails;

  public CoreException(String message) {
    this(message, null, null);
  }

  public CoreException(String message, String code) {
    this(message, code, null);
  }
}
