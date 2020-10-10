package com.trickl.mock.model.bindings;

import com.trickl.model.pricing.primitives.OrderBook;
import reactor.core.publisher.Flux;

public class StreamClient {
  public Flux<OrderBook> subscribeQuotes(String instrumentCode) {
    MockInstrumentImpl instrument = MockInstruments.get(instrumentCode);
    return instrument.getOrderBookStream();
  }
}
