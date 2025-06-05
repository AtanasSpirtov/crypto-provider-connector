package com.example.cryptoconnector.sdk.trade.binance;

import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.WebSocketCallback;
import com.example.cryptoconnector.sdk.trade.auth.service.CredentialService;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BinanceReadOperationService {

  private final CredentialService credentialService;

  private final Map<String, List<Integer>> activeConnectionStreams = new ConcurrentHashMap<>();

  private final WebSocketStreamClientImpl binanceClient = new WebSocketStreamClientImpl();

  public ResponseResult streamMarketDataForClient(String clientId, List<String> pairs) {
    if (activeConnectionStreams.containsKey(clientId)) {
      return new ResponseResult("Client already has active streams.");
    }

    List<Integer> connectionIds = new ArrayList<>();

    pairs.forEach(pair -> {
      int id = binanceClient.tradeStream(pair.toLowerCase(), sendMessagesToKafka(clientId, pair.toLowerCase()));
      connectionIds.add(id);
    });

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
    return event -> {
      System.out.printf("[%s] Trade event for %s: %s%n", clientId, symbol, event);
    };
  }
}
