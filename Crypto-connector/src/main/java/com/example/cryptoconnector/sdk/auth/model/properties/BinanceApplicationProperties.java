package com.example.cryptoconnector.sdk.auth.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "binance")
public class BinanceApplicationProperties {
  private final String clientId;
  private final String clientSecret;
  private final String redirectUri;
}
