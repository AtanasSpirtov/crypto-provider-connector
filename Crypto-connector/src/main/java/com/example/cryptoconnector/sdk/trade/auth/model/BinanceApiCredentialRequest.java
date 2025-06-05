package com.example.cryptoconnector.sdk.trade.auth.model;

import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BinanceApiCredentialRequest extends ApiCredentialsRequest{
  public BinanceApiCredentialRequest(@NotBlank String apiKey, @NotBlank String apiSecret) {
    super(apiKey, apiSecret, Provider.BINANCE);
  }
}
