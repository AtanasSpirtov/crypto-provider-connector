package com.example.cryptoconnector.sdk.oauth2.config;

import java.util.List;

import com.example.cryptoconnector.sdk.oauth2.provider.CoinbaseOAuth2Provider;
import com.example.cryptoconnector.sdk.oauth2.provider.OAuth2Provider;
import com.example.cryptoconnector.sdk.oauth2.service.OAuth2ProviderRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OAuth2ProvidersConfig {
    
    private final OAuth2ProviderRegistry providerRegistry;
    private final List<OAuth2Provider> coinbaseProviders;

    @Bean
    public ApplicationRunner registerOAuth2Providers() {
        return args -> {
          coinbaseProviders.forEach(providerRegistry::registerProvider);
        };
    }
} 