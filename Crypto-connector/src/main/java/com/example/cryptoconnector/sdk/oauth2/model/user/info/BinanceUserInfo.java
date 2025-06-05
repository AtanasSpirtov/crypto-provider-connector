package com.example.cryptoconnector.sdk.oauth2.model.user.info;

import lombok.Getter;

@Getter
public class BinanceUserInfo extends UserInfo {
  public BinanceUserInfo(String id, String email, String name) {
    super(id, email, name);
  }
}
