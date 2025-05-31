package com.example.cryptoconnector.sdk.auth.model.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "coinbase")
public class CoinbaseApplicationProperties {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
}
