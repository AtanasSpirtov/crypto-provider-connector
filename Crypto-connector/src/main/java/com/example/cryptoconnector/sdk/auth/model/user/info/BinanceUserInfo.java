package com.example.cryptoconnector.sdk.auth.model.user.info;

import lombok.Getter;

@Getter
public class BinanceUserInfo extends UserInfo {
  public BinanceUserInfo(String id, String email, String name) {
    super(id, email, name);
  }
}
