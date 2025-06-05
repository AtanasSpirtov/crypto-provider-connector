package com.example.cryptoconnector.sdk.oauth2.feign;

import com.example.cryptoconnector.sdk.oauth2.model.apiresponse.CoinbaseUserInfoResponse;
import com.example.cryptoconnector.sdk.oauth2.model.apiresponse.token.CoinbaseTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "coinbaseOAuth", url = "https://api.coinbase.com")
public interface CoinbaseAuthFeignClient {

    @PostMapping(value = "/oauth/token")
    CoinbaseTokenResponse exchangeCode(@RequestBody Map<String, String> body);

    @GetMapping("/v2/user")
    CoinbaseUserInfoResponse getUserInfo(@RequestHeader("Authorization") String bearerToken);

    @PostMapping("/oauth/token")
    CoinbaseTokenResponse refreshToken(@RequestBody Map<String, String> body);


}