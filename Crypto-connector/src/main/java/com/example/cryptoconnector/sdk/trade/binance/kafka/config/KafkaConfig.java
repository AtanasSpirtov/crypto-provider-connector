package com.example.cryptoconnector.sdk.trade.binance.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

  private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // <-- Kafka broker
  private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081"; // <-- Confluent Schema Registry

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS); // broker
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put("schema.registry.url", SCHEMA_REGISTRY_URL);

    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768); // 32 KB
    props.put(ProducerConfig.LINGER_MS_CONFIG, 5); // 5 ms
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432L); // 32 MB

    props.put(ProducerConfig.RETRIES_CONFIG, 1); // retry attempts
    props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // 1 sec between retries

    props.put(ProducerConfig.ACKS_CONFIG, "all"); // all replicas to acknowledge
    props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 3000); // 3 sec
    props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000); // 1 sec

    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
    props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1048576); // 1 MB

    return props;
  }

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
