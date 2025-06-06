package com.example.cryptoconnector.sdk.oauth2.model.apiresponse.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BinanceTokenResponse extends TokenResponse{

  @JsonProperty("token_type")
  private final String tokenType;

  @JsonProperty("expires_in")
  private final String expiresIn;

  @JsonProperty("scope")
  private final String scope;

  public BinanceTokenResponse(String refreshToken, String accessToken, String tokenType, String expiresIn, String scope ) {
    super(refreshToken, accessToken);
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.scope = scope;
  }
}
