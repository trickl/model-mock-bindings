package com.trickl.mock.model.bindings;

import com.google.common.base.CaseFormat;
import com.trickl.model.instrument.MockInstrument;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MockInstruments {
  public static final MockInstrumentImpl FAST_RANDOM_WALK;
  public static final MockInstrumentImpl STD_RANDOM_WALK;
  public static final MockInstrumentImpl SLOW_RANDOM_WALK;

  private static final HashMap<String, MockInstrumentImpl> instrumentsByCode;

  public static final List<MockInstrumentImpl> getAll() {
    return instrumentsByCode.values().stream().collect(Collectors.toList());
  }

  public static final MockInstrumentImpl get(String code) {
    return instrumentsByCode.get(code);
  }

  static {
    instrumentsByCode = new HashMap<String, MockInstrumentImpl>();
    FAST_RANDOM_WALK = buildRandomWalk(
        "Fast Random Walk", 
        "Generate a random walk between 0 and 100, starting at 50, new price every half second.",
        50, 0, 100, 0.5, 500);
    STD_RANDOM_WALK = buildRandomWalk(
        "Standard Random Walk", 
        "Generate a random walk between 0 and 100, starting at 50, new price every second.",
        50, 0, 100, 0.5, 1000);
    SLOW_RANDOM_WALK = buildRandomWalk(
      "Slow Random Walk", 
      "Generate a random walk between 0 and 100, starting at 50, new price every five second.",
      50, 0, 100, 0.5, 5000);
  }

  private MockInstruments() {
    // Utility library  
  }

  private static RandomWalkInstrument buildRandomWalk(
      String name, String description, 
      int start, int minBound, int maxBound, double spread, long frequencyMs) {
    String nameUpperUnderscore = name.toUpperCase().replaceAll("[^\\p{L}\\p{Nd}]+", "_");
    String code = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, nameUpperUnderscore);
    RandomWalkInstrument instrument = RandomWalkInstrument.builder()
        .definition(MockInstrument.builder()
          .code(code)
          .exchangeId("mock")
          .name(name)
          .description(description)
        .build())
        .start(start)
        .minBound(minBound)
        .maxBound(maxBound)
        .spread(spread)
        .frequencyMs(frequencyMs)
        .build();

    instrumentsByCode.put(instrument.getDefinition().getCode(), instrument);

    return instrument;
  }
}
