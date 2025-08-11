package com.capitole.challenge.starwars.application.exception;

public class EntityNotFoundException extends CoreException {
  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, String code) {
    super(message, code);
  }
}
