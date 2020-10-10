package com.trickl.mock.model.bindings;

import com.trickl.model.instrument.MockInstrument;
import com.trickl.model.pricing.primitives.OrderBook;
import reactor.core.publisher.Flux;

public interface MockInstrumentImpl {
  MockInstrument getDefinition();

  Flux<OrderBook> getOrderBookStream();
}
