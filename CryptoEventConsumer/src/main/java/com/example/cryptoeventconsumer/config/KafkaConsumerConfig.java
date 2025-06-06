package com.example.cryptoeventconsumer.config;

import com.example.cryptoconnector.avro.BinanceMarketEvent;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  private static final String BOOTSTRAP_SERVERS = "localhost:9092";          // Broker
  private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081"; // Confluent Schema Registry
  private static final String GROUP_ID = "binance-consumer-group";           // Kafka consumer group ID

  @Bean
  public ConsumerFactory<String, BinanceMarketEvent> binanceEventConsumerFactory() {
    Map<String, Object> props = new HashMap<>();

    // Core Kafka consumer settings
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Read from beginning if no committed offset

    // Avro deserialization
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true); // <-- use SpecificRecord
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
    props.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, false); //  schema must exist in registry
    props.put(KafkaAvroDeserializerConfig.USE_LATEST_VERSION, true);     // always use latest schema version

    // Performance and reliability
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);          // <-- manual commits
    props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
    props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
    props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);

    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BinanceMarketEvent> binanceEventKafkaListenerContainerFactory() {

    var factory = new ConcurrentKafkaListenerContainerFactory<String, BinanceMarketEvent>();
    factory.setConsumerFactory(binanceEventConsumerFactory());

    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); // <-- manual ack
    factory.setBatchListener(true);       // <-- enable batch consumption
    factory.setConcurrency(3);            // <-- 3 consumer threads
    factory.setCommonErrorHandler(new DefaultErrorHandler()); // <-- basic error handling

    return factory;
  }
}
