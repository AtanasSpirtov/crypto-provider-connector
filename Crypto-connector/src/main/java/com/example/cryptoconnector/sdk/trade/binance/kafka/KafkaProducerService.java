package com.example.cryptoconnector.sdk.trade.binance.kafka;

import com.example.cryptoconnector.avro.BinanceMarketEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  private final KafkaTemplate<String, BinanceMarketEvent> kafkaTemplate;
  private final KafkaProperties kafkaProperties;

  public void sendMessageWithMetadata(String clientId, String symbol, String rawJsonEvent) {
    BinanceMarketEvent event = BinanceMarketEvent.newBuilder()
        .setClientId(clientId)
        .setSymbol(symbol)
        .setRawJson(rawJsonEvent)
        .setTimestamp(Instant.now().toEpochMilli())
        .build();

    ProducerRecord<String, BinanceMarketEvent> record = new ProducerRecord<>(
        kafkaProperties.getTopics().getMarketEvents(),
        clientId,
        event
    );
    record.headers().add(new RecordHeader("symbol", symbol.getBytes(StandardCharsets.UTF_8)));
    kafkaTemplate.send(record);
  }
}
