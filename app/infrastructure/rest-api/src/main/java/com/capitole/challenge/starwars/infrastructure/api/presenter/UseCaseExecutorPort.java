package com.capitole.challenge.starwars.infrastructure.api.presenter;

import com.capitole.challenge.starwars.application.port.UseCase;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Interface to use Functional approach to execute any Use Case.
 */
public interface UseCaseExecutorPort {

  /**
   * To use with DeferredResult (async resolution)
   * @param input input
   * @param useCase use case
   * @param outputFunction output function to be applied. Normally a mapper to Dto.
   * @return DeferredResult
   * @param <I> Input
   * @param <O> Output
   * @param <R> Result type
   */
  default < I extends UseCase.InputValues, O extends UseCase.OutputValues, R> DeferredResult<R> execute(
      I input,
      UseCase<I, O> useCase,
      Function<O, R> outputFunction
  ) {
    return deferredResult(completableFuture(input, useCase, outputFunction));
  }

  /**
   * To use with CompletableFuture (async resolution)
   * @param input input
   * @param useCase use case
   * @param outputFunction output function to be applied. Normally a mapper to Dto.
   * @return Future.
   * @param <I> Input
   * @param <O> Output
   * @param <R> Result type
   */
  default <I extends UseCase.InputValues, O extends UseCase.OutputValues, R> CompletableFuture<R> completableFuture(
      I input,
      UseCase<I, O> useCase,
      Function<O, R> outputFunction
  ) {
    return CompletableFuture
        .supplyAsync(() -> input)
        .thenApplyAsync(useCase::execute)
        .thenApplyAsync(outputFunction);
  }

  /**
   * To use in Synchronous approaches
   * @param input input
   * @param useCase use case
   * @param outputFunction output function to be applied. Normally a mapper to Dto.
   * @return the result with type R.
   * @param <I> Input
   * @param <O> Output
   * @param <R> Result type
   */
  default <I extends UseCase.InputValues, O extends UseCase.OutputValues, R> R functional(
      I input,
      UseCase<I, O> useCase,
      Function<O, R> outputFunction
  ) {
    return outputFunction.apply(useCase.execute(input));
  }

  /**
   * To use with CompletableFuture (blocking resolution)
   * @param input input
   * @param useCase use case
   * @param outputFunction output function to be applied. Normally a mapper to Dto.
   * @return Future in blocking way.
   * @param <I> Input
   * @param <O> Output
   * @param <R> Result type
   */
  default <I extends UseCase.InputValues, O extends UseCase.OutputValues, R> R executeBlocking(
      I input,
      UseCase<I, O> useCase,
      Function<O, R> outputFunction
  ) {
    return completableFuture(input, useCase, outputFunction).join();
  }

  /**
   * CompletableFuture with DeferredResult
   * @param future future
   * @return DeferredResult
   * @param <T> Type inside the future
   */
  default <T> DeferredResult<T> deferredResult(CompletableFuture<T> future) {
    DeferredResult<T> deferredResult = new DeferredResult<>();
    future
        .thenAccept(deferredResult::setResult)
        .exceptionally(ex -> {
          if (ex instanceof CompletionException) {
            deferredResult.setErrorResult(ex.getCause());
          } else {
            deferredResult.setErrorResult(ex);
          }
          return null;
        });
    return deferredResult;
  }
}

