package com.example.cryptoconnector.sdk.auth.provider;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.cryptoconnector.sdk.auth.feign.BinanceAuthFeignClient;
import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfo;
import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfoWrapper;
import com.example.cryptoconnector.sdk.auth.model.enums.Provider;
import com.example.cryptoconnector.sdk.auth.model.apiresponse.token.BinanceTokenResponse;
import com.example.cryptoconnector.sdk.auth.service.request.builder.AuthRequestBuilder;

@Component
public class BinanceOAuth2Provider extends OAuth2Provider {

  private final BinanceAuthFeignClient binanceAuthFeignClient;

  @Qualifier("binanceAuthRequestBuilder")
  private final AuthRequestBuilder binanceBuilder;

  public BinanceOAuth2Provider(BinanceAuthFeignClient binanceAuthFeignClient,
      @Qualifier("binanceAuthRequestBuilder") AuthRequestBuilder requestBuilder) {
    super(Provider.BINANCE);
    this.binanceAuthFeignClient = binanceAuthFeignClient;
    this.binanceBuilder = requestBuilder;
  }

  @Override
  public String buildAuthorizationUrl(String state) {
    return binanceBuilder.buildLoginUrl(state);
  }

  @Override
  public BinanceTokenResponse exchangeCodeForToken(String code) {
    return binanceAuthFeignClient.exchangeCode(buildTokenExchangeRequest(code));
  }

  @Override
  public BinanceTokenResponse refreshAccessToken(String refreshToken) {
    return binanceAuthFeignClient.refreshToken(buildTokenRefreshRequest(refreshToken));
  }

  @Override
  public UserInfoWrapper<UserInfo> getUserInfo(String accessToken) {
//    BinanceUserInfo response = binanceAuthFeignClient
//        .getUserInfo(buildBearerHeader(accessToken));
//    return UserInfoWrapper.builder().userInfo(response).build();
    throw new UnsupportedOperationException();
  }

  @Override
  public String[] getRequiredScopes() {
    return new String[] { "user:info", "wallet:read" };
  }

  @Override
  protected Map<String, String> buildTokenExchangeRequest(String code) {
    return binanceBuilder.buildAuthCodeRequest(code);
  }

  @Override
  protected Map<String, String> buildTokenRefreshRequest(String refreshToken) {
    return binanceBuilder.buildRefreshRequest(refreshToken);
  }
}
