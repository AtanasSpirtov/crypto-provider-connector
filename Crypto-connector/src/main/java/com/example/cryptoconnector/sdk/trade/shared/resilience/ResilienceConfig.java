package com.example.cryptoconnector.sdk.trade.shared.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Configuration
public class ResilienceConfig {

  @Bean
  public Map<CircuitBreakerApplicationEnum, CircuitBreaker> circuitBreakerMap(CircuitBreakerRegistry registry) {
    return Arrays.stream(CircuitBreakerApplicationEnum.values()).map(thirdPartyProvider ->
        new AbstractMap.SimpleEntry<>(
            thirdPartyProvider,
            registry.circuitBreaker(thirdPartyProvider.name().toLowerCase()))
    ).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
