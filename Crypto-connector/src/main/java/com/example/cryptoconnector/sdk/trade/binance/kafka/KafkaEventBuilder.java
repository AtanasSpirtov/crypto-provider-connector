package com.example.cryptoconnector.sdk.trade.binance.kafka;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.example.cryptoconnector.avro.BinanceMarketEvent;

@Component
public class KafkaEventBuilder {

  public BinanceMarketEvent getBinanceMarketEvent(String clientId, String symbol, String rawJsonEvent) {
    return BinanceMarketEvent.newBuilder()
        .setClientId(clientId)
        .setSymbol(symbol)
        .setRawJson(rawJsonEvent)
        .setTimestamp(Instant.now().toEpochMilli())
        .build();
  }
}
