package com.example.cryptoconnector.sdk.trade.auth.model;

import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiCredentialsRequest {

  @NotBlank
  private final String apiKey;

  @NotBlank
  private final String apiSecret;

  @NotBlank
  private final Provider provider;
}
