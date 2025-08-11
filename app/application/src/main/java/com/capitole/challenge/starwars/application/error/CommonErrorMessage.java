package com.capitole.challenge.starwars.application.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Common error message enum that has all possible common messages for all common failure scenarios.
 *
 * @see ErrorMessage ErrorMessage
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorMessage implements ErrorMessage {
  AUTH_FAILED("AUTH_001", "Authorization failed"),
  MESSAGE_NOT_READABLE("COMMON_001", "Request parsing failed"),
  ARGUMENT_NOT_VALID("COMMON_002", "Invalid input format"),
  RESOURCE_NOT_FOUND("COMMON_003", "Resource not found"),
  REQUEST_NOT_ACCEPTABLE("COMMON_006", "Media type is not acceptable"),
  CONFLICT("COMMON_004", "Conflict"),
  INTERNAL_SERVER_ERROR("COMMON_005", "Unhandled exception occurred");

  /** Error message unique code */
  private final String code;

  /** Error detailed message */
  private final String message;
}
