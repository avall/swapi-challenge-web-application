package com.capitole.challenge.main;

import com.capitole.challenge.domain.decorator.Interactor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Main spring-boot application taking care to load
 * into the Spring Context any class that implements the Interactor.
 */
@ComponentScan(
    basePackages = {"com.capitole.challenge.*"},
    includeFilters = @ComponentScan.Filter(
    type = FilterType.ANNOTATION,
    classes = {Interactor.class})
)
@SpringBootApplication
public class StarwarsApplication {

  public static void main(String[] args) {
    SpringApplication.run(StarwarsApplication.class, args);
  }
}
