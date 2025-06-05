package com.example.cryptoconnector.sdk.trade.binance;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("binance/read/market-data")
@RequiredArgsConstructor
public class BinanceReadOperationController {
  private final BinanceReadOperationService binanceReadOperationService;

  @GetMapping("stream/{clientId}")
  public ResponseEntity<ResponseResult> initiateClientStream(@PathVariable String clientId, @RequestParam List<String> pairs) {
    return ResponseEntity.ok(binanceReadOperationService.streamMarketDataForClient(clientId, pairs));
  }

  @GetMapping("close/{clientId}/")
  public ResponseEntity<ResponseResult> closeClientStream(@PathVariable String clientId) {
    return ResponseEntity.ok(binanceReadOperationService.closeMarketStreamForClient(clientId));
  }
}
