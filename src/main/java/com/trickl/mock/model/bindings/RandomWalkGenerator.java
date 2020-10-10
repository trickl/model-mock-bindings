package com.trickl.mock.model.bindings;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class RandomWalkGenerator implements Supplier<Integer> {    
  private final int start;
  private final int minBound;
  private final int maxBound;
  private Random random;
  private AtomicInteger value; 

  /**
   * Create a random walk supplier.
   * @param seed the random seed
   * @param start the starting value
   * @param minBound the minimum value
   * @param maxBound the maximum value   
   */
  public RandomWalkGenerator(long seed, int start, int minBound, int maxBound) {
    this.start = start;
    this.minBound = minBound;
    this.maxBound = maxBound;
    random = new Random(seed);
    value = new AtomicInteger(start);
  }

  @Override
  public Integer get() {
    return value.getAndUpdate(last -> {
      int next;
      if (random.nextDouble() > 0.5) {          
        if (last < maxBound) {
          next = last + 1;   
        } else {
          next = start;
        }      
      } else {          
        if (last > minBound) {
          next = last - 1;
        } else {
          next = start;
        }
      }

      return next;
    });
  }    
}