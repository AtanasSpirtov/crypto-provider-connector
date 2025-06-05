package com.example.cryptoconnector.sdk.oauth2.model.apiresponse.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

  @JsonProperty("refresh_token")
  private final String refreshToken;

  @JsonProperty("access_token")
  private final String accessToken;
}
