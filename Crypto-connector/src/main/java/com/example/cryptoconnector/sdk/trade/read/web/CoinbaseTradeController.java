package com.example.cryptoconnector.sdk.trade.read.web;

import com.example.cryptoconnector.sdk.trade.read.service.CoinbaseAdvancedTradeApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coinbase")
@RequiredArgsConstructor
public class CoinbaseTradeController {
    private final CoinbaseAdvancedTradeApi coinbaseAdvancedTradeApi;

    @GetMapping("get/{clientId}")
    public ResponseEntity<String> getCoinbaseClient(@PathVariable String clientId) {
        return ResponseEntity.ok(coinbaseAdvancedTradeApi.nicee(clientId));
    }
}
