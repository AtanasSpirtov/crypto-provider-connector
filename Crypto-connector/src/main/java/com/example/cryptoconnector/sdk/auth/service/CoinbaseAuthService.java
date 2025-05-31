package com.example.cryptoconnector.sdk.auth.service;

import com.example.cryptoconnector.sdk.auth.feign.CoinbaseAuthFeighClient;
import com.example.cryptoconnector.sdk.auth.model.CoinbaseUserInfo;
import com.example.cryptoconnector.sdk.auth.model.TokenResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CoinbaseAuthService {

    private final CoinbaseAuthFeighClient coinbaseOAuthFeignClient;
    private final CoinbaseAuthRequestBuilder builder;

    private final Map<String, CoinbaseUserInfo> coinbaseClientCached = new ConcurrentHashMap<>();

    public void saveNewClientByCodeInCache(String code) {
        TokenResponse token = coinbaseOAuthFeignClient.exchangeCode(builder.buildAuthCodeRequest(code));
        CoinbaseUserInfo userInfo = coinbaseOAuthFeignClient
                .getUserInfo(builder.buildBearerHeader(token.accessToken()))
                .data()
                .setAccessToken(token.accessToken())
                .setRefreshToken(token.refreshToken());

        coinbaseClientCached.put(userInfo.getId(), userInfo);
    }

    public CoinbaseUserInfo getLatestUserInfo(String userId) {
        CoinbaseUserInfo current = getUserInfoCached(userId);
        try {
            return coinbaseOAuthFeignClient
                    .getUserInfo(builder.buildBearerHeader(current.getAccessToken()))
                    .data();
        } catch (FeignException.Unauthorized e) {
            return refreshToken(userId, current);
        }
    }

    private CoinbaseUserInfo refreshToken(String userId, CoinbaseUserInfo cachedUser) {
        TokenResponse refreshed = coinbaseOAuthFeignClient.refreshToken(
                builder.buildRefreshRequest(cachedUser.getRefreshToken()));
        CoinbaseUserInfo updated = coinbaseOAuthFeignClient
                .getUserInfo(builder.buildBearerHeader(refreshed.accessToken()))
                .data()
                .setAccessToken(refreshed.accessToken())
                .setRefreshToken(refreshed.refreshToken());
        coinbaseClientCached.put(userId, updated);
        return updated;
    }

    public String getCoinbaseLoginUrl() {
        return builder.buildLoginUrl(UUID.randomUUID().toString());
    }

    public CoinbaseUserInfo getUserInfoCached(String userId) {
        if (!coinbaseClientCached.containsKey(userId)) {
            throw new RuntimeException("Cannot refresh token for non authenticated user");
        }
        return coinbaseClientCached.get(userId);
    }
}
