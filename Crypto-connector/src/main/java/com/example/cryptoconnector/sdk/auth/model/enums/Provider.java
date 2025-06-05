package com.example.cryptoconnector.sdk.auth.model.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Provider {
  COINBASE("gdax"), BINANCE("binance");

  private final String loginUrlValue;

  Provider(String loginUrlValue) {
    this.loginUrlValue = loginUrlValue;
  }

  public static Provider fromValue(String loginUrlValue) {
    return Arrays.stream(Provider.values())
        .filter(provider -> provider.getLoginUrlValue().equals(loginUrlValue))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown loginUrlValue: " + loginUrlValue));
  }
}
