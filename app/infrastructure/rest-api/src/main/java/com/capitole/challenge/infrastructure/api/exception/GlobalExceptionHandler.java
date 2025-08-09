package com.capitole.challenge.infrastructure.api.exception;

import com.capitole.challenge.api.lib.model.ErrorDto;
import com.capitole.challenge.application.error.CommonErrorMessage;
import com.capitole.challenge.application.exception.EntityNotFoundException;
import static com.capitole.challenge.infrastructure.api.exception.ExceptionUtils.buildInvalidDetails;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler.
 *
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  public static final String MS_STARWARS = "ms-swapi";

  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ResponseBody
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ErrorDto handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return ErrorDto.builder()
            .code(CommonErrorMessage.MESSAGE_NOT_READABLE.getCode())
            .message(CommonErrorMessage.MESSAGE_NOT_READABLE.getMessage())
            .source(MS_STARWARS).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ResponseBody
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    var invalidParams = new HashMap<String, String>();
    invalidParams.put(
        e.getParameter().getParameterName(), e.getMessage());
    return ErrorDto.builder()
        .code(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode())
        .source(MS_STARWARS)
        .message(e.getMessage())
        .details(buildInvalidDetails(invalidParams))
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ResponseBody
  @ExceptionHandler({MissingRequestHeaderException.class})
  public ErrorDto handleMissingServletRequestParameterException(MissingRequestHeaderException e) {
    var invalidParams = new HashMap<String, String>();
    invalidParams.put(e.getParameter().getParameterName(), e.getMessage());

    return ErrorDto.builder()
        .correlationId(UUID.randomUUID().toString())
        .code(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode())
        .source(MS_STARWARS)
        .message(e.getMessage())
        .details(buildInvalidDetails(invalidParams))
        .build();
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    var invalidParams = new HashMap<String, String>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      invalidParams.put(error.getField(), error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      invalidParams.put(error.getObjectName(), error.getDefaultMessage());
    }
    return ErrorDto.builder()
        .correlationId(UUID.randomUUID().toString())
        .code(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode())
        .source(MS_STARWARS)
        .message(ex.getMessage())
        .details(buildInvalidDetails(invalidParams))
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
    var invalidParams = new HashMap<String, String>();
    e.getConstraintViolations()
        .forEach(
            v -> {
              String field = null;
              var message = v.getMessage();

              for (Path.Node node : v.getPropertyPath()) {
                field = node.getName();
              }
              invalidParams.put(field, message);
            });
    return ErrorDto.builder()
        .correlationId(UUID.randomUUID().toString())
        .code(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode())
        .source(MS_STARWARS)
        .message(e.getMessage())
        .details(buildInvalidDetails(invalidParams))
        .build();
  }


  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorDto requestNoHandlerFound() {
    return ErrorDto.builder()
        .code(CommonErrorMessage.RESOURCE_NOT_FOUND.getCode())
        .message(CommonErrorMessage.RESOURCE_NOT_FOUND.getMessage())
        .source(MS_STARWARS).build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(EntityNotFoundException.class)
  public ErrorDto requestEntityNotFound(EntityNotFoundException e) {
    return ErrorDto.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .source(MS_STARWARS).build();
  }

  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) // 406
  @ResponseBody
  @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
  public ErrorDto handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    return ErrorDto.builder()
        .code(CommonErrorMessage.REQUEST_NOT_ACCEPTABLE.getCode())
        .message(CommonErrorMessage.REQUEST_NOT_ACCEPTABLE.getMessage())
        .source(MS_STARWARS).build();
  }

  @ResponseStatus(code = HttpStatus.CONFLICT) //  409
  @ResponseBody
  @ExceptionHandler(SQLException.class)
  public ErrorDto handleConflict(SQLException e) {
    return ErrorDto.builder()
        .source(MS_STARWARS)
        .code(CommonErrorMessage.CONFLICT.getCode())
        .message(NestedExceptionUtils.getMostSpecificCause(e).getMessage())
        .build();
  }

  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //  409
  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  public ErrorDto handleRuntimeException(RuntimeException e) {
    return ErrorDto.builder()
        .code(CommonErrorMessage.INTERNAL_SERVER_ERROR.getCode())
        .message(CommonErrorMessage.INTERNAL_SERVER_ERROR.getMessage())
        .trace(getTrace(e))
        .source(MS_STARWARS).build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ErrorDto handleUnknownException(Exception e) {
    return ErrorDto.builder()
        .code(CommonErrorMessage.INTERNAL_SERVER_ERROR.getCode())
        .message(CommonErrorMessage.INTERNAL_SERVER_ERROR.getMessage())
        .trace(getTrace(e))
        .source(MS_STARWARS).build();
  }

  private String getTrace(Exception e) {
    return Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
        .collect(Collectors.joining("\n"));
  }


}
