package com.example.cryptoconnector.sdk.trade.binance;

import io.github.resilience4j.circuitbreaker.*;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.cryptoconnector.sdk.trade.shared.resilience.CircuitBreakerApplicationEnum;

@Component
@RequiredArgsConstructor
public class BinanceClientWrapper {
  private final Map<CircuitBreakerApplicationEnum, CircuitBreaker> circuitBreakerMap;

  public <R> R callWithCircuitBreaker(Supplier<R> supplier) {
    CircuitBreaker cb = circuitBreakerMap.get(CircuitBreakerApplicationEnum.BINANCE);
    Supplier<R> decorated = CircuitBreaker.decorateSupplier(cb, supplier);
    return Try.ofSupplier(decorated)
        .getOrElseThrow(throwable -> new RuntimeException("Binance failed with exception: ", throwable));
  }
}
