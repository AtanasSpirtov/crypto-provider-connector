package com.example.cryptoconnector.sdk.auth.service.request.builder;

import com.example.cryptoconnector.sdk.auth.model.properties.BinanceApplicationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component("binanceAuthRequestBuilder")
public class BinanceAuthRequestBuilder implements AuthRequestBuilder {

  private final BinanceApplicationProperties applicationProperties;

  public BinanceAuthRequestBuilder(BinanceApplicationProperties appProps) {
    this.applicationProperties = appProps;
  }

  @Override
  public Map<String, String> buildAuthCodeRequest(String code) {
    return Map.of(
        "grant_type", "authorization_code",
        "code", code,
        "client_id", applicationProperties.getClientId(),
        "client_secret", applicationProperties.getClientSecret(),
        "redirect_uri", applicationProperties.getRedirectUri()
    );
  }

  @Override
  public Map<String, String> buildRefreshRequest(String refreshToken) {
    return Map.of(
        "grant_type", "refresh_token",
        "refresh_token", refreshToken,
        "client_id", applicationProperties.getClientId(),
        "client_secret", applicationProperties.getClientSecret()
    );
  }

  @Override
  public String buildBearerHeader(String token) {
    return "Bearer " + token;
  }

  @Override
  public String buildLoginUrl(String state) {
    return UriComponentsBuilder.fromUriString("https://www.binance.com/oauth/authorize")
        .queryParam("response_type", "code")
        .queryParam("client_id", applicationProperties.getClientId())
        .queryParam("redirect_uri", applicationProperties.getRedirectUri())
        .queryParam("scope", "user:info user:accounts")
        .queryParam("state", state)
        .build()
        .toUriString();
  }
}
