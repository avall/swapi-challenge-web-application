package com.capitole.challenge.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(
    classes = {StarwarsApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = {})
class StarwarsApplicationTest {
  @Autowired ApplicationContext applicationContext;

  @Test
  void testMain() {
    // Setup
    // Run the test
    Assertions.assertAll(
        // Use Cases
        () -> Assertions.assertNotNull(applicationContext.getBean("getPeopleInteractor")),
        () -> Assertions.assertNotNull(applicationContext.getBean("getStarshipsInteractor")),

        // Adapters
        () -> Assertions.assertNotNull(applicationContext.getBean("peopleAdapter")),
        () -> Assertions.assertNotNull(applicationContext.getBean("starshipAdapter")),

        // Repositories
        () -> Assertions.assertNotNull(applicationContext.getBean("starwarsClient"))
    );
  }
}
