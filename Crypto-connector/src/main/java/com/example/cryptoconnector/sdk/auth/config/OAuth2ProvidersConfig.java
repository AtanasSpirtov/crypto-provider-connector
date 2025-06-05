package com.example.cryptoconnector.sdk.auth.config;

import com.example.cryptoconnector.sdk.auth.provider.CoinbaseOAuth2Provider;
import com.example.cryptoconnector.sdk.auth.service.OAuth2ProviderRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OAuth2ProvidersConfig {
    
    private final OAuth2ProviderRegistry providerRegistry;
    private final CoinbaseOAuth2Provider coinbaseProvider;

    @Bean
    public ApplicationRunner registerOAuth2Providers() {
        return args -> {
            providerRegistry.registerProvider(coinbaseProvider);
            
            // Future providers can be registered here:
            // providerRegistry.registerProvider(binanceProvider);
            // providerRegistry.registerProvider(krakenProvider);
        };
    }
} 