package com.example.cryptoconnector.sdk.oauth2.provider;

import com.example.cryptoconnector.sdk.oauth2.feign.CoinbaseAuthFeignClient;
import com.example.cryptoconnector.sdk.oauth2.model.user.info.CoinbaseUserInfo;
import com.example.cryptoconnector.sdk.oauth2.model.user.info.UserInfo;
import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;
import com.example.cryptoconnector.sdk.oauth2.model.apiresponse.token.CoinbaseTokenResponse;
import com.example.cryptoconnector.sdk.oauth2.model.user.info.UserInfoWrapper;
import com.example.cryptoconnector.sdk.oauth2.service.request.builder.AuthRequestBuilder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoinbaseOAuth2Provider extends OAuth2Provider {

  private final CoinbaseAuthFeignClient coinbaseAuthFeignClient;

  @Qualifier("coinbaseAuthRequestBuilder")
  private final AuthRequestBuilder coinbaseBuilder;

  public CoinbaseOAuth2Provider(CoinbaseAuthFeignClient coinbaseAuthFeignClient,
      @Qualifier("coinbaseAuthRequestBuilder") AuthRequestBuilder requestBuilder) {
    super(Provider.COINBASE);
    this.coinbaseAuthFeignClient = coinbaseAuthFeignClient;
    this.coinbaseBuilder = requestBuilder;
  }

  @Override
  public String buildAuthorizationUrl(String state) {
    return coinbaseBuilder.buildLoginUrl(state);
  }

  @Override
  public CoinbaseTokenResponse exchangeCodeForToken(String code) {
    return coinbaseAuthFeignClient.exchangeCode(buildTokenExchangeRequest(code));
  }

  @Override
  public CoinbaseTokenResponse refreshAccessToken(String refreshToken) {
    return coinbaseAuthFeignClient.refreshToken(buildTokenRefreshRequest(refreshToken));
  }

  @Override
  public UserInfoWrapper<UserInfo> getUserInfo(String accessToken) {
    CoinbaseUserInfo response = coinbaseAuthFeignClient
        .getUserInfo(buildBearerHeader(accessToken)).data();
    return UserInfoWrapper.builder().userInfo(response).build();
  }

  @Override
  public String[] getRequiredScopes() {
    return new String[] { "wallet:user:read", "wallet:accounts:read" };
  }

  @Override
  protected Map<String, String> buildTokenExchangeRequest(String code) {
    return coinbaseBuilder.buildAuthCodeRequest(code);
  }

  @Override
  protected Map<String, String> buildTokenRefreshRequest(String refreshToken) {
    return coinbaseBuilder.buildRefreshRequest(refreshToken);
  }
} 