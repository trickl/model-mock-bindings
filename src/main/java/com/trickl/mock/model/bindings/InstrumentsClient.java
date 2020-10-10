package com.trickl.mock.model.bindings;

import com.trickl.model.instrument.MockInstrument;
import java.util.List;
import java.util.stream.Collectors;

public class InstrumentsClient {
  /**
   * Get all instruments.
   * @return a list of all instruments.
   */
  public List<MockInstrument> getAll() {
    return MockInstruments.getAll().stream()
        .map(MockInstrumentImpl::getDefinition)
        .collect(Collectors.toList());
  }

  /**
   * Get a specific instrument by code.
   * @param instrumentCode The instrument code
   * @return the specific instrument
   */
  public MockInstrument get(String instrumentCode) {
    MockInstrumentImpl instrument = MockInstruments.get(instrumentCode);
    if (instrument == null) {
      return null;
    }

    return instrument.getDefinition();
  }
}
