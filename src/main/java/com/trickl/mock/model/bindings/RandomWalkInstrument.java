package com.trickl.mock.model.bindings;

import com.trickl.flux.publishers.FixedRateTimePublisher;
import com.trickl.model.instrument.MockInstrument;
import com.trickl.model.pricing.primitives.OrderBook;
import com.trickl.model.pricing.primitives.Quote;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

@Builder
public class RandomWalkInstrument implements MockInstrumentImpl {
  
  @Getter
  private final MockInstrument definition;
 
  @Builder.Default
  private int seed = 123456;

  @Builder.Default
  private int start = 50;

  @Builder.Default
  private int minBound = 0;

  @Builder.Default
  private int maxBound = 99;

  @Builder.Default
  private double spread = 0.5;

  @Builder.Default
  private Long frequencyMs = 1000L;
  
  protected OrderBook nextOrderBook(RandomWalkGenerator generator, Instant time, double spread) {
    double midPrice = (double) generator.get();
    double halfSpread = spread / 2;
    return OrderBook.builder()
      .time(time)      
      .bid(Quote.builder().price(BigDecimal.valueOf(midPrice - halfSpread))
      .build())
      .ask(Quote.builder().price(BigDecimal.valueOf(midPrice + halfSpread))
      .build())
    .build();
  }

  /**
   * Get a price stream using a random walk generator.
   * @return A price stream
   */
  public Flux<OrderBook> getOrderBookStream() {
    FixedRateTimePublisher fixedRate = new FixedRateTimePublisher(Duration.ofMillis(frequencyMs));
    RandomWalkGenerator generator = new RandomWalkGenerator(seed, start, minBound, maxBound);
    return fixedRate.get()
        .map(time -> nextOrderBook(generator, time, spread));
  }
}
