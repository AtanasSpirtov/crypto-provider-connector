package com.example.cryptoconnector.sdk.trade.auth.model;

import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CoinbaseApiCredentialsRequest extends ApiCredentialsRequest {

  private final String passphrase;

  public CoinbaseApiCredentialsRequest(@NotBlank String apiKey, @NotBlank String apiSecret, String passphrase) {
    super(apiKey, apiSecret, Provider.COINBASE);
    this.passphrase = passphrase;
  }
}