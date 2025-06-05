package com.example.cryptoconnector.sdk.trade.binance.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaProperties {

  private Topics topics;
  private Batch batch;

  @Data
  public static class Topics {
    private String marketEvents;
    private String orderEvents;
  }

  @Data
  public static class Batch {
    private int size;
    private int lingerMs;
  }
}

