package com.capitole.challenge.starwars.infrastructure.api.presenter;

import com.capitole.challenge.starwars.application.port.UseCase;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.async.DeferredResult;

class UseCaseExecutorPortTest {
  @Test
  void givenUseCaseExecuteAsDeferredResult() {
    // Given
    UseCase<Input, Output> useCase = input ->
        Output.builder().output(input.input + "Output").build();
    Input input = Input.builder().input("Input").build();
    Function<Output, String> outputMapper = o -> o.output + "Test";

    // When
    UseCaseExecutorPort port = new UseCaseExecutorPort() {};
    DeferredResult<String> result = port.execute(input, useCase, outputMapper);

    // Then
    Assertions.assertEquals("InputOutputTest", result.getResult());
  }

  @Test
  void givenUseCaseRunFunctional() {
    // Given
    UseCase<Input, Output> useCase = input ->
        Output.builder().output(input.input + "Output").build();
    Input input = Input.builder().input("Input").build();
    Function<Output, String> outputMapper = o -> o.output + "Test";

    // When
    UseCaseExecutorPort port = new UseCaseExecutorPort() {};
    String result = port.functional(input, useCase, outputMapper);

    // Then
    Assertions.assertEquals("InputOutputTest", result);
  }

  @Test
  void givenUseCaseRunExecuteBlocking() {
    // Given
    UseCase<Input, Output> useCase = input ->
        Output.builder().output(input.input + "Output").build();
    Input input = Input.builder().input("Input").build();
    Function<Output, String> outputMapper = o -> o.output + "Test";

    // When
    UseCaseExecutorPort port = new UseCaseExecutorPort() {};
    String result = port.executeBlocking(input, useCase, outputMapper);

    // Then
    Assertions.assertEquals("InputOutputTest", result);
  }

  /**
   * Method under test: {@link UseCaseExecutorPort#deferredResult(CompletableFuture)}
   */
  @Test
  void testDeferredResult() {
    // Arrange and Act
    DeferredResult<Object> actualDeferredResultResult = (new UseCaseExecutorPort() {})
        .deferredResult(new CompletableFuture<>());

    // Assert
    assertNull(actualDeferredResultResult.getResult());
    assertFalse(actualDeferredResultResult.hasResult());
    assertFalse(actualDeferredResultResult.isSetOrExpired());
  }

  /**
   * Method under test: {@link UseCaseExecutorPort#deferredResult(CompletableFuture)}
   */
  @Test
  void testDeferredResultThrowable() {
    // Arrange
    CompletableFuture<Object> future = new CompletableFuture<>();
    Throwable throwable = new Throwable();
    future.obtrudeException(throwable);

    // Act
    DeferredResult<Object> actualDeferredResultResult = (new UseCaseExecutorPort() {})
        .deferredResult(future);

    // Assert
    assertTrue(actualDeferredResultResult.hasResult());
    assertTrue(actualDeferredResultResult.isSetOrExpired());
    assertSame(throwable, actualDeferredResultResult.getResult());
  }

  @Builder
  static class Input implements UseCase.InputValues {
    String input;
  }

  @Builder
  static class Output implements UseCase.OutputValues {
    String output;
  }
}
