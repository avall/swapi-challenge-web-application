package com.capitole.challenge.starwars.main;

import com.capitole.challenge.starwars.domain.decorator.Interactor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Main spring-boot application taking care to load
 * into the Spring Context any class that implements the Interactor.
 */
@ComponentScan(
    basePackages = {"com.capitole.challenge.starwars.*"},
    includeFilters = @ComponentScan.Filter(
    type = FilterType.ANNOTATION,
    classes = {Interactor.class})
)
@SpringBootApplication
@EnableCaching
public class StarwarsApplication {

  public static void main(String[] args) {
    SpringApplication.run(StarwarsApplication.class, args);
  }
}
