package com.example.cryptoeventconsumer.service;

import com.example.cryptoconnector.avro.BinanceMarketEvent;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoEventProcessingService {

    public void processMarketEvent(BinanceMarketEvent event, Map<String, Object> tradeData, Map<String, Object> headers) {
        try {
            log.info("Processing market event for client: {}, symbol: {}", 
                event.getClientId(), event.getSymbol());

            TradeInfo tradeInfo = extractTradeInfo(event, tradeData);

            log.info("Successfully processed market event for client: {}, symbol: {}, price: {}", 
                event.getClientId(), event.getSymbol(), tradeInfo.getPrice());
                
        } catch (Exception e) {
            log.error("Error processing market event for client: {} and symbol: {}: {}", 
                event.getClientId(), event.getSymbol(), e.getMessage(), e);
            throw new RuntimeException("Failed to process market event", e);
        }
    }

    private TradeInfo extractTradeInfo(BinanceMarketEvent event, Map<String, Object> tradeData) {
        TradeInfo.TradeInfoBuilder builder = TradeInfo.builder()
            .clientId(event.getClientId())
            .symbol(event.getSymbol())
            .timestamp(Instant.ofEpochMilli(event.getTimestamp()));

        if (tradeData.containsKey("p")) {
            builder.price(new BigDecimal(tradeData.get("p").toString()));
        }
        if (tradeData.containsKey("q")) {
            builder.quantity(new BigDecimal(tradeData.get("q").toString()));
        }
        if (tradeData.containsKey("T")) {
            builder.tradeTime(Instant.ofEpochMilli(Long.parseLong(tradeData.get("T").toString())));
        }
        if (tradeData.containsKey("m")) {
            builder.isBuyerMaker(Boolean.parseBoolean(tradeData.get("m").toString()));
        }
        if (tradeData.containsKey("t")) {
            builder.tradeId(Long.parseLong(tradeData.get("t").toString()));
        }
        return builder.build();
    }
}