package com.example.cryptoconnector.sdk.auth.feign;

import com.example.cryptoconnector.sdk.auth.model.CoinbaseUserInfoResponse;
import com.example.cryptoconnector.sdk.auth.model.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "coinbaseOAuth", url = "https://api.coinbase.com")
public interface CoinbaseAuthFeighClient {

    @PostMapping(value = "/oauth/token")
    TokenResponse exchangeCode(@RequestBody Map<String, String> body);

    @GetMapping("/v2/user")
    CoinbaseUserInfoResponse getUserInfo(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/oauth/token")
    TokenResponse refreshToken(@RequestBody Map<String, String> body);


}