package com.trickl.mock.model.bindings;

import com.trickl.flux.routing.MapRouter;
import com.trickl.model.pricing.primitives.OrderBook;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class StreamClient {
  private MapRouter<OrderBook> mapRouter = MapRouter
      .<OrderBook>builder()
      .fluxCreator(this::generateQuotes)
      .build();

  private Flux<OrderBook> generateQuotes(Publisher<OrderBook> source, String instrumentCode) {
    MockInstrumentImpl instrument = MockInstruments.get(instrumentCode);
    return instrument.getOrderBookStream();
  }

  public Flux<OrderBook>  getQuotes(String instrumentCode) {
    return mapRouter.route(Flux.empty(), instrumentCode);
  }
}
