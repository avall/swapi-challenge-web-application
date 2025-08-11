package com.capitole.challenge.main;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PeopleControllerIT {

  @LocalServerPort private int port;

  @ParameterizedTest
  @MethodSource("additionalArguments")
  void getPeople(String name, String sortBy, String sortOrder, int sizeExpected, String firstNameExpected, String createdExpected) {
    given()
        .port(port)
        .queryParam("name", name)
        .queryParam("sort_by", sortBy)
        .queryParam("sort_order", sortOrder)
        .when()
        .get("/v1/people")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("content.size()", equalTo(sizeExpected))
        .body("content[0].name", equalTo(firstNameExpected))
        .body("content[0].created", equalTo(createdExpected))
    ;
  }

  @Test
  void getPeopleNotFound() {
    given()
        .port(port)
        .queryParam("name", "zzzzzzzzzz")
        .when()
        .get("/v1/people")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("content.size()", equalTo(0));
  }

  static Stream<Arguments> additionalArguments() {
    return Stream.of(
        Arguments.of("ara", "created", "desc", 2, "Luminara Unduli", "2014-12-20T16:45:53.668")

    );
  }
}
