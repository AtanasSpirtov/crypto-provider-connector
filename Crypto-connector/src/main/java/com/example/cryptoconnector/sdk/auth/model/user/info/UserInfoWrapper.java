package com.example.cryptoconnector.sdk.auth.model.user.info;

import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoWrapper<T extends UserInfo> {
  private final T userInfo;
  private String accessToken;
  private String refreshToken;
  private final String userId = "";

  public UserInfoWrapper(T userInfo, String accessToken, String refreshToken) {
    this.userInfo = userInfo;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
