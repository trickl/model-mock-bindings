package com.trickl.model.bindings;

import static com.trickl.assertj.core.api.JsonObjectAssertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trickl.mock.model.bindings.InstrumentsClient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ConversionTest {

  private static ObjectMapper objectMapper;

  /** Setup the tests. */
  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @ParameterizedTest
  @MethodSource("sourceProvider")
  public void testConversion(Object obj) {
    String outputDirectory = getOutputDirectory().toString();
    assertThat(obj)
        .usingObjectMapper(objectMapper)
        .usingProjectDirectory(outputDirectory)
        .serializesAsExpected();
  }

  static Stream<Object> sourceProvider() {
    return Stream.of(
        new InstrumentsClient().get("standard_random_walk"));
  }

  private static Path getProjectDirectory() {
    String testPath =
        ConversionTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    return Paths.get(testPath.substring(0, testPath.indexOf("target")), "/src/test/resources/");
  }

  private static Path getOutputDirectory() {
    return getProjectDirectory().resolve("output");
  }
}
