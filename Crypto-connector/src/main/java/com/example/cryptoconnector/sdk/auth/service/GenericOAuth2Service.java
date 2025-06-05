package com.example.cryptoconnector.sdk.auth.service;

import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfo;
import com.example.cryptoconnector.sdk.auth.model.user.info.UserInfoWrapper;
import com.example.cryptoconnector.sdk.auth.model.enums.Provider;
import com.example.cryptoconnector.sdk.auth.model.apiresponse.token.TokenResponse;
import com.example.cryptoconnector.sdk.auth.provider.OAuth2Provider;

import feign.FeignException;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenericOAuth2Service {

  private final Map<Provider, Map<String, UserInfoWrapper<UserInfo>>> providerUserCache = new ConcurrentHashMap<>();

  public void saveNewClientByCode(OAuth2Provider provider, String code) {
    TokenResponse token = provider.exchangeCodeForToken(code);
    UserInfoWrapper<UserInfo> userInfo = provider.getUserInfo(token.getAccessToken());
    userInfo.setAccessToken(token.getAccessToken());
    userInfo.setRefreshToken(token.getRefreshToken());

    Map<String, UserInfoWrapper<UserInfo>> userCache = providerUserCache.computeIfAbsent(provider.getProviderId(), k -> new ConcurrentHashMap<>());
    userCache.put(userInfo.getUserId(), userInfo);
  }

  public UserInfoWrapper<UserInfo> getLatestUserInfo(OAuth2Provider provider, String userId) {
    UserInfoWrapper<UserInfo> cachedUser = getUserInfoCached(provider, userId);

    try {
      return provider.getUserInfo(cachedUser.getAccessToken());
    } catch (FeignException.Unauthorized e) {
      return refreshTokenAndGetUserInfo(provider, userId, cachedUser);
    }
  }

  private UserInfoWrapper<UserInfo> refreshTokenAndGetUserInfo(OAuth2Provider provider, String userId, UserInfoWrapper<UserInfo> cachedUser) {

    TokenResponse refreshedToken = provider.refreshAccessToken(cachedUser.getRefreshToken());
    UserInfoWrapper<UserInfo> updatedUserInfo = provider.getUserInfo(refreshedToken.getAccessToken());

    Map<String, UserInfoWrapper<UserInfo>> userCache = providerUserCache.get(provider.getProviderId());
    userCache.put(userId, updatedUserInfo);
    return updatedUserInfo;
  }

  public String getLoginUrl(OAuth2Provider provider) {
    return provider.buildAuthorizationUrl(UUID.randomUUID().toString());
  }

  public UserInfoWrapper<UserInfo> getUserInfoCached(OAuth2Provider provider, String userId) {
    if (!providerUserCache.containsKey(provider.getProviderId())) {
      throw new RuntimeException("Cannot find provider id");
    }

    Map<String, UserInfoWrapper<UserInfo>> userCache = providerUserCache.get(provider.getProviderId());

    if (!userCache.containsKey(userId)) {
      throw new RuntimeException(String.format("Cannot find user id %s for provider id %s", provider.getProviderId(), userId));
    }

    return userCache.get(userId);

  }
} 