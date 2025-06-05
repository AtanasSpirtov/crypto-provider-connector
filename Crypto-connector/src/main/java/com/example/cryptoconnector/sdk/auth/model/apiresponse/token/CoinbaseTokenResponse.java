package com.example.cryptoconnector.sdk.auth.model.apiresponse.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CoinbaseTokenResponse extends TokenResponse {

  @JsonProperty("token_type")
  private final String tokenType;

  @JsonProperty("expires_in")
  private final String expiresIn;

  @JsonProperty("scope")
  private final String scope;

  public CoinbaseTokenResponse(String refreshToken, String accessToken, String tokenType, String expiresIn, String scope ) {
    super(refreshToken, accessToken);
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.scope = scope;
  }
}
