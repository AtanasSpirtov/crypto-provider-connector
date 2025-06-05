package com.example.cryptoconnector.sdk.oauth2.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.cryptoconnector.sdk.oauth2.model.user.info.BinanceUserInfo;
import com.example.cryptoconnector.sdk.oauth2.model.apiresponse.token.BinanceTokenResponse;

@FeignClient(name = "binanceOAuth", url = "https://api.binance.com")
public interface BinanceAuthFeignClient {

  @PostMapping("/oauth/token")
  BinanceTokenResponse exchangeCode(@RequestBody Map<String, String> tokenRequest);

  @PostMapping("/oauth/token")
  BinanceTokenResponse refreshToken(@RequestBody Map<String, String> refreshRequest);

  @GetMapping("/oauth/userinfo")
  BinanceUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
