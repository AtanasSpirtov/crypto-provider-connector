package com.example.cryptoconnector.sdk.auth.service.request.builder;

import com.example.cryptoconnector.sdk.auth.model.enums.GrantTypeEnum;
import com.example.cryptoconnector.sdk.auth.model.properties.CoinbaseApplicationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component("coinbaseAuthRequestBuilder")
public class CoinbaseAuthRequestBuilder implements AuthRequestBuilder {

  private final CoinbaseApplicationProperties applicationProperties;

  public CoinbaseAuthRequestBuilder(CoinbaseApplicationProperties appProps) {
    this.applicationProperties = appProps;
  }

  @Override
  public Map<String, String> buildAuthCodeRequest(String code) {
    return Map.of(
        "grant_type", GrantTypeEnum.AUTHENTICATE.getValue(),
        "code", code,
        "client_id", applicationProperties.getClientId(),
        "client_secret", applicationProperties.getClientSecret(),
        "redirect_uri", applicationProperties.getRedirectUri()
    );
  }

  @Override
  public Map<String, String> buildRefreshRequest(String refreshToken) {
    return Map.of(
        "grant_type", GrantTypeEnum.REFRESH.getValue(),
        "refresh_token", refreshToken,
        "client_id", applicationProperties.getClientId(),
        "client_secret", applicationProperties.getClientSecret()
    );
  }

  @Override
  public String buildBearerHeader(String token) {
    return String.format("Bearer %s", token);
  }

  @Override
  public String buildLoginUrl(String state) {
    return UriComponentsBuilder.fromUriString("https://www.coinbase.com/oauth/authorize")
        .queryParam("response_type", "code")
        .queryParam("client_id", applicationProperties.getClientId())
        .queryParam("redirect_uri", applicationProperties.getRedirectUri())
        .queryParam("scope", "wallet:user:read wallet:accounts:read")
        .queryParam("state", state)
        .build()
        .toUriString();
  }
}
