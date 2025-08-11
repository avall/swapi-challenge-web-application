package com.capitole.challenge.starwars.application.port;


import com.capitole.challenge.starwars.application.exception.CoreException;

/**
 * To enforce any use case to have always input and output values.
 * @param <I> Input values to be processed by the use case
 * @param <O> output values returned after processing
 */
@FunctionalInterface
public interface UseCase<I extends UseCase.InputValues , O extends UseCase.OutputValues> {
  O execute(I input) throws CoreException;

  interface InputValues {}

  interface OutputValues {}

}