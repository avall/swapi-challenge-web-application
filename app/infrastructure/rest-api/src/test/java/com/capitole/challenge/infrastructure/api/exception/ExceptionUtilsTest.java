package com.capitole.challenge.infrastructure.api.exception;

import com.capitole.challenge.api.lib.model.ErrorDetailsInnerDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ExceptionUtilsTest {

  /**
   * Method under test: {@link ExceptionUtils#buildInvalidDetails(Map)}
   */
  @Test
  void testBuildInvalidDetailsEmpty() {
    // Arrange and Act
    List<ErrorDetailsInnerDto> actualBuildInvalidDetailsResult = ExceptionUtils.buildInvalidDetails(
        new HashMap<>());

    // Assert
    assertTrue(actualBuildInvalidDetailsResult.isEmpty());
  }

  /**
   * Method under test: {@link ExceptionUtils#buildInvalidDetails(Map)}
   */
  @Test
  void testBuildInvalidDetails() {
    // Arrange
    HashMap<String, String> invalidDetails = new HashMap<>();
    invalidDetails.put("foo", "foo");

    // Act
    List<ErrorDetailsInnerDto> actualBuildInvalidDetailsResult = ExceptionUtils.buildInvalidDetails(
        invalidDetails);

    // Assert
    assertEquals(1, actualBuildInvalidDetailsResult.size());
    ErrorDetailsInnerDto getResult = actualBuildInvalidDetailsResult.get(0);
    assertEquals("foo", getResult.getField());
    assertEquals("foo", getResult.getValue());
    assertNull(getResult.getCode());
    assertNull(getResult.getMessage());
    assertNull(getResult.getPattern());
    assertNull(getResult.getSource());
    assertNull(getResult.getMaxLength());
    assertNull(getResult.getMinLength());
    assertNull(getResult.getPossibleValues());
  }
}
