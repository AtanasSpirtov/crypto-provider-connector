package com.example.cryptoeventconsumer.service;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeInfo {
    private String clientId;
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private Instant timestamp;
    private Instant tradeTime;
    private Long tradeId;
    private boolean isBuyerMaker;
}
