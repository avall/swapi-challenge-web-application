package com.capitole.challenge.starwars.application.exception;

/**
 * @author alex.vall
 *
 * Not Found exception to be used by all the application layers.
 */
public class EntityNotFoundException extends CoreException {
  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, String code) {
    super(message, code);
  }
}
