package com.example.cryptoconnector.sdk.trade.binance;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.WebSocketCallback;
import com.example.cryptoconnector.sdk.trade.auth.service.CredentialService;
import com.example.cryptoconnector.sdk.trade.binance.kafka.KafkaProducerService;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BinanceReadOperationService {

  private final KafkaProducerService kafkaProducerService;

  private final Map<String, List<Integer>> activeConnectionStreams = new ConcurrentHashMap<>();

  private final WebSocketStreamClient binanceClient = new WebSocketStreamClientImpl();

  private final BinanceClientWrapper binanceClientWrapper;

  public ResponseResult streamMarketDataForClient(String clientId, List<String> pairs) {
    if (activeConnectionStreams.containsKey(clientId)) {
      return new ResponseResult("Client already has active streams.");
    }

    List<Integer> connectionIds = pairs.stream().map(pair ->
      binanceClientWrapper.callWithCircuitBreaker(() ->
          binanceClient.tradeStream(pair.toLowerCase(), sendMessagesToKafka(clientId, pair.toLowerCase()))
    )).toList();

    activeConnectionStreams.put(clientId, connectionIds);
    return new ResponseResult(String.format("Market stream started for client: %s", clientId));
  }

  public ResponseResult closeMarketStreamForClient(String clientId) {
    List<Integer> connectionIds = activeConnectionStreams.remove(clientId);

    if (connectionIds == null || connectionIds.isEmpty()) {
      return new ResponseResult("No active stream found for client: " + clientId);
    }

    connectionIds.forEach(binanceClient::closeConnection);
    return new ResponseResult("Closed all market streams for client: " + clientId);
  }

  @PreDestroy
  public void shutdown() {
    activeConnectionStreams.values().forEach(list -> list.forEach(binanceClient::closeConnection));
    activeConnectionStreams.clear();
  }

  private WebSocketCallback sendMessagesToKafka(String clientId, String symbol) {
    return event -> kafkaProducerService.sendMessageWithMetadata(clientId, symbol, event);
  }
}
