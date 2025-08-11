package com.capitole.challenge.infrastructure.api.exception;

import com.capitole.challenge.api.lib.model.ErrorDto;
import com.capitole.challenge.application.error.CommonErrorMessage;
import com.capitole.challenge.application.exception.EntityNotFoundException;
import static com.capitole.challenge.infrastructure.api.exception.GlobalExceptionHandler.MS_STARWARS;
import jakarta.validation.ConstraintViolationException;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {

  public static final String PARAM = "param";
  public static final String REASON = "Reason";
  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  @Test
  void givenException_whenRequestHttpMediaTypeNotAcceptable_thenReturnsErrorDto() {
    // Given
    HttpMediaTypeNotAcceptableException exception = new HttpMediaTypeNotAcceptableException("");

    // When
    ErrorDto errorDto = exceptionHandler.handleHttpMediaTypeNotAcceptableException(exception);

    // Then
    Assertions.assertEquals(CommonErrorMessage.REQUEST_NOT_ACCEPTABLE.getCode(),
        errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.REQUEST_NOT_ACCEPTABLE.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenRequestEntityNotFound_thenReturnsErrorDto() {
    // Given
    EntityNotFoundException exception = new EntityNotFoundException(
        CommonErrorMessage.RESOURCE_NOT_FOUND.getMessage(),
        CommonErrorMessage.RESOURCE_NOT_FOUND.getCode());
    // When
    ErrorDto errorDto = exceptionHandler.requestEntityNotFound(exception);
    // Then
    Assertions.assertEquals(CommonErrorMessage.RESOURCE_NOT_FOUND.getCode(), errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.RESOURCE_NOT_FOUND.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenRequestConstraintViolation_thenReturnsErrorDto() {
    // Given
    ConstraintViolationException exception = new ConstraintViolationException(new HashSet<>());

    // When
    ErrorDto errorDto = exceptionHandler.handleConstraintViolationException(exception);

    // Then
    Assertions.assertEquals(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode(), errorDto.getCode());
    Assertions.assertTrue(errorDto.getMessage().isBlank());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }


  public void exampleMethod(String exampleParameter) {
    // Sample method with one parameter
  }

  @Test
  void givenException_whenMissingServletRequestParameter_thenReturnsErrorDto()
      throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
    // Given
    Method method = GlobalExceptionHandlerTest.class.getMethod("exampleMethod", String.class);
    MethodParameter methodParameter = new MethodParameter(method, 0);

    Field parameterNameField = MethodParameter.class.getDeclaredField("parameterName");
    parameterNameField.setAccessible(true);

    parameterNameField.set(methodParameter, PARAM);

    MissingRequestHeaderException exception = new MissingRequestHeaderException("header",
        methodParameter);

    // When
    ErrorDto errorDto = exceptionHandler.handleMissingServletRequestParameterException(exception);

    // Then
    Assertions.assertEquals(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode(), errorDto.getCode());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
    Assertions.assertEquals(
        "Required request header 'header' for method parameter type String is not present",
        errorDto.getMessage());
    Assertions.assertEquals(PARAM, errorDto.getDetails().get(0).getField());
  }

  @Test
  void givenException_whenMethodArgumentTypeMismatch_thenReturnsErrorDto()
      throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
    // Given
    Method method = GlobalExceptionHandlerTest.class.getMethod("exampleMethod", String.class);
    MethodParameter methodParameter = new MethodParameter(method, 0);

    Field parameterNameField = MethodParameter.class.getDeclaredField("parameterName");
    parameterNameField.setAccessible(true);

    parameterNameField.set(methodParameter, PARAM);

    MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException("value",
        String.class, PARAM, methodParameter, null);

    // When
    ErrorDto errorDto = exceptionHandler.handleMethodArgumentTypeMismatchException(exception);

    // Then
    Assertions.assertEquals(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode(), errorDto.getCode());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
    Assertions.assertEquals(
        "Method parameter 'param': Failed to convert value of type 'java.lang.String' to required type 'java.lang.String'",
        errorDto.getMessage());
    Assertions.assertEquals(PARAM, errorDto.getDetails().get(0).getField());
  }

  @Test
  void givenException_whenMessageNotReadable_thenReturnsErrorDto() {
    // Given
    HttpMessageNotReadableException exception = new HttpMessageNotReadableException("message",
        new HttpInputMessage() {
          @Override
          public HttpHeaders getHeaders() {
            return new HttpHeaders();
          }

          @Override
          public InputStream getBody() {
            return new InputStream() {
              @Override
              public int read() throws IOException {
                return 0;
              }
            };
          }
        });
    // When
    ErrorDto errorDto = exceptionHandler.handleHttpMessageNotReadableException(exception);
    // Then
    Assertions.assertEquals(CommonErrorMessage.MESSAGE_NOT_READABLE.getCode(), errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.MESSAGE_NOT_READABLE.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenMethodArgumentNotValid_thenReturnsErrorDto()
      throws NoSuchMethodException {

    // Given
    Method method = GlobalExceptionHandlerTest.class.getMethod("exampleMethod", String.class);
    MethodParameter methodParameter = new MethodParameter(method, 0);

    // Paso 2: Crear un BindingResult con errores de validación
    ObjectError error = new ObjectError("name", "Name cannot be empty");

    BindingResult bindingResult = new BindingResult() {
      // Implementación mínima de BindingResult para propósitos de demostración
      // Métodos no implementados
      @Override
      public void addError(ObjectError error) {
      }

      @Override
      public String getObjectName() {
        return null;
      }

      @Override
      public void setNestedPath(String nestedPath) {
      }

      @Override
      public String getNestedPath() {
        return null;
      }

      @Override
      public void reject(String errorCode) {
      }

      @Override
      public void reject(String errorCode, String defaultMessage) {
      }

      @Override
      public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
      }

      @Override
      public void rejectValue(String field, String errorCode) {
      }

      @Override
      public void rejectValue(String field, String errorCode, String defaultMessage) {
      }

      @Override
      public void rejectValue(String field, String errorCode, Object[] errorArgs,
          String defaultMessage) {
      }

      @Override
      public boolean hasErrors() {
        return false;
      }

      @Override
      public int getErrorCount() {
        return 0;
      }

      @Override
      public ObjectError getGlobalError() {
        return error;
      }

      @Override
      public List<ObjectError> getGlobalErrors() {
        return List.of(error);
      }

      @Override
      public int getFieldErrorCount() {
        return 0;
      }

      @Override
      public List<FieldError> getFieldErrors() {
        return List.of();
      }

      @Override
      public int getFieldErrorCount(String field) {
        return 0;
      }

      @Override
      public Object getFieldValue(String field) {
        return null;
      }

      @Override
      public Class<?> getFieldType(String field) {
        return null;
      }

      @Override
      public Object getTarget() {
        return null;
      }

      @Override
      public Map<String, Object> getModel() {
        return null;
      }

      @Override
      public Object getRawFieldValue(String field) {
        return null;
      }

      @Override
      public PropertyEditorRegistry getPropertyEditorRegistry() {
        return null;
      }

      @Override
      public PropertyEditor findEditor(String field, Class<?> valueType) {
        return null;
      }

      @Override
      public boolean hasGlobalErrors() {
        return true;
      }

      @Override
      public boolean hasFieldErrors() {
        return false;
      }

      @Override
      public boolean hasFieldErrors(String field) {
        return false;
      }

      @Override
      public String[] resolveMessageCodes(String errorCode) {
        return new String[0];
      }

      @Override
      public String[] resolveMessageCodes(String errorCode, String field) {
        return new String[0];
      }

      @Override
      public void addAllErrors(Errors errors) {
      }
    };

    // Paso 3: Crear una instancia de MethodArgumentNotValidException
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter,
        bindingResult);

    // When
    ErrorDto errorDto = exceptionHandler.handleMethodArgumentNotValid(exception);

    // Then
    Assertions.assertEquals(CommonErrorMessage.ARGUMENT_NOT_VALID.getCode(), errorDto.getCode());
    Assertions.assertEquals(
        "Validation failed for argument [0] in public void com.capitole.challenge.infrastructure.api.exception.GlobalExceptionHandlerTest.exampleMethod(java.lang.String): [Error in object 'name': codes []; arguments []; default message [Name cannot be empty]] ",
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenNoHandlerFound_thenReturnsErrorDto() {
    // Given
    // When
    ErrorDto errorDto = exceptionHandler.requestNoHandlerFound();

    // Then
    Assertions.assertEquals(CommonErrorMessage.RESOURCE_NOT_FOUND.getCode(), errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.RESOURCE_NOT_FOUND.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenRuntime_thenReturnsErrorDto() {
    // Given
    // When
    ErrorDto errorDto = exceptionHandler.handleRuntimeException(new RuntimeException());

    // Then
    Assertions.assertEquals(CommonErrorMessage.INTERNAL_SERVER_ERROR.getCode(), errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.INTERNAL_SERVER_ERROR.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenUnknown_thenReturnsErrorDto() {
    // Given
    // When
    ErrorDto errorDto = exceptionHandler.handleUnknownException(new Exception());

    // Then
    Assertions.assertEquals(CommonErrorMessage.INTERNAL_SERVER_ERROR.getCode(), errorDto.getCode());
    Assertions.assertEquals(CommonErrorMessage.INTERNAL_SERVER_ERROR.getMessage(),
        errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }

  @Test
  void givenException_whenConflict_thenReturnsErrorDto() {
    // Given
    // When
    ErrorDto errorDto = exceptionHandler.handleConflict(new SQLException(REASON,"State"));

    // Then
    Assertions.assertEquals(CommonErrorMessage.CONFLICT.getCode(), errorDto.getCode());
    Assertions.assertEquals(REASON, errorDto.getMessage());
    Assertions.assertEquals(MS_STARWARS, errorDto.getSource());
  }
}