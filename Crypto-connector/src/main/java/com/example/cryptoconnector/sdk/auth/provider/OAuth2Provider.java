package com.example.cryptoconnector.sdk.auth.provider;

import com.example.cryptoconnector.sdk.auth.model.enums.Provider;
import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfo;
import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfoWrapper;
import com.example.cryptoconnector.sdk.auth.model.apiresponse.token.TokenResponse;

import java.util.Map;

import lombok.Getter;

@Getter
public abstract class OAuth2Provider {

  protected final Provider providerId;

  protected OAuth2Provider(Provider providerId) {
    this.providerId = providerId;
  }

  public abstract String buildAuthorizationUrl(String state);

  public abstract TokenResponse exchangeCodeForToken(String code);

  public abstract TokenResponse refreshAccessToken(String refreshToken);

  public abstract UserInfoWrapper<UserInfo> getUserInfo(String accessToken);

  public abstract String[] getRequiredScopes();

  protected abstract Map<String, String> buildTokenExchangeRequest(String code);

  protected abstract Map<String, String> buildTokenRefreshRequest(String refreshToken);

  protected String buildBearerHeader(String accessToken) {
    return String.format("Bearer %s", accessToken);
  }
} 