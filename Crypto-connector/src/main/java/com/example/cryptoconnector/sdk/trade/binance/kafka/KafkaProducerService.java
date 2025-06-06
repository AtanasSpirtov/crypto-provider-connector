package com.example.cryptoconnector.sdk.trade.binance.kafka;

import com.example.cryptoconnector.avro.BinanceMarketEvent;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  private final KafkaTemplate<String, BinanceMarketEvent> kafkaTemplate;
  private final KafkaEventBuilder builder;
  private final KafkaProperties kafkaProperties;

  public void sendMessageWithMetadata(String clientId, String symbol, String rawJsonEvent) {
    BinanceMarketEvent event = builder.getBinanceMarketEvent(clientId, symbol, rawJsonEvent);

    ProducerRecord<String, BinanceMarketEvent> record = new ProducerRecord<>(
        kafkaProperties.getTopics().getMarketEvents(),
        clientId,
        event);
    record.headers().add(new RecordHeader("symbol", symbol.getBytes(StandardCharsets.UTF_8)));
    System.out.println(record);
    kafkaTemplate.send(record);
  }
}
