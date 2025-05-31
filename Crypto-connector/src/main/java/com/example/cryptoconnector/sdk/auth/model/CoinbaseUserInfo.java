package com.example.cryptoconnector.sdk.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseUserInfo {

    private final String id;
    private final String email;
    private final String name;

    @JsonProperty("profile_url")
    private final String profileUrl;

    @JsonProperty("avatar_url")
    private final String avatarUrl;

    private String refreshToken;

    private String accessToken;

    public CoinbaseUserInfo(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("profile_url") String profileUrl,
            @JsonProperty("avatar_url") String avatarUrl
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.avatarUrl = avatarUrl;
    }

    public CoinbaseUserInfo setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public CoinbaseUserInfo setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
