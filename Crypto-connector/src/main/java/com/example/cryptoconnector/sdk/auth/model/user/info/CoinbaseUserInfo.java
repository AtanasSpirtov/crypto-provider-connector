package com.example.cryptoconnector.sdk.auth.model.user.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseUserInfo extends UserInfo {

    @JsonProperty("profile_url")
    private final String profileUrl;

    @JsonProperty("avatar_url")
    private final String avatarUrl;

    public CoinbaseUserInfo(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("profile_url") String profileUrl,
            @JsonProperty("avatar_url") String avatarUrl
    ) {
      super(id, email, name);
      this.profileUrl = profileUrl;
        this.avatarUrl = avatarUrl;
    }
}
