package com.capitole.challenge.starwars.application.error;

/**
 * Interface used for describing allowed behavior of any error details enum that implements it.
 *
 * @since 1.0.0
 */
public interface ErrorMessage {

  /**
   * Gets the error message's unique code
   *
   * @return String code
   */
  String getCode();

  /**
   * Gets the error message's detailed message
   *
   * @return String message
   */
  String getMessage();
}
