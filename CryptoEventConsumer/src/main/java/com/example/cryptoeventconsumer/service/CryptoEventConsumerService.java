package com.example.cryptoeventconsumer.service;

import com.example.cryptoconnector.avro.BinanceMarketEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoEventConsumerService {

  private final ObjectMapper objectMapper = new ObjectMapper();
    private final CryptoEventProcessingService processingService;

    @KafkaListener(
        topics = "market.crypto.binance.stream",
        containerFactory = "binanceEventKafkaListenerContainerFactory",
        groupId = "binance-consumer-group"
    )
    @CircuitBreaker(name = "kafka-consumer", fallbackMethod = "fallbackProcessEvents")
    public void consumeBinanceMarketEvents(
            @Payload List<BinanceMarketEvent> events,
            @Headers Map<String, Object> headers,
            Acknowledgment acknowledgment) {
        
        log.info("Received batch of {} Binance market events", events.size());
        
        try {
            processEventsBatch(events, headers);
            acknowledgment.acknowledge();
            log.debug("Successfully processed and acknowledged batch of {} events", events.size());
        } catch (Exception e) {
            log.error("Error processing batch of {} events: {}", events.size(), e.getMessage(), e);
            throw e;
        }
    }

    private void processEventsBatch(List<BinanceMarketEvent> events, Map<String, Object> headers) {
      events.forEach(event -> {
        try {
          processIndividualEvent(event, headers);
        } catch (Exception e) {
          log.error("Error processing individual event for client {} and symbol {}: {}",
              event.getClientId(), event.getSymbol(), e.getMessage(), e);
        }
      });
    }

    private void processIndividualEvent(BinanceMarketEvent event, Map<String, Object> headers) {
        log.debug("Processing event for client: {}, symbol: {}, timestamp: {}", 
            event.getClientId(), event.getSymbol(), 
            Instant.ofEpochMilli(event.getTimestamp()));
        try {
            Map<String, Object> tradeData = parseRawJson(event.getRawJson());
            
            processingService.processMarketEvent(event, tradeData, headers);
            
            log.debug("Successfully processed event for client: {} and symbol: {}", 
                event.getClientId(), event.getSymbol());
                
        } catch (JsonProcessingException e) {
            log.error("Failed to parse raw JSON for event from client: {} and symbol: {}", 
                event.getClientId(), event.getSymbol(), e);
            throw new RuntimeException("JSON parsing failed", e);
        }
    }

    private Map<String, Object> parseRawJson(String rawJson) throws JsonProcessingException {
        return objectMapper.readValue(rawJson, Map.class);
    }

    // Circuit breaker fallback method
    public void fallbackProcessEvents(
            List<BinanceMarketEvent> events,
            Map<String, Object> headers,
            Acknowledgment acknowledgment,
            Exception ex) {
        
        log.warn("Circuit breaker activated for processing {} events. Reason: {}", 
            events.size(), ex.getMessage());
        
        for (BinanceMarketEvent event : events) {
            log.warn("Fallback: Skipping event for client: {} and symbol: {} due to circuit breaker", 
                event.getClientId(), event.getSymbol());
        }
        acknowledgment.acknowledge();
    }
} 