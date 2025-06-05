package com.example.cryptoconnector.sdk.oauth2.service;

import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;
import com.example.cryptoconnector.sdk.oauth2.provider.OAuth2Provider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Registry for managing OAuth2 providers.
 * This allows the application to support multiple providers simultaneously.
 */
@Service
public class OAuth2ProviderRegistry {
    
    private final Map<Provider, OAuth2Provider> providers = new HashMap<>();

    public void registerProvider(OAuth2Provider provider) {
        providers.put(provider.getProviderId(), provider);
    }

    public Optional<OAuth2Provider> getProvider(Provider providerId) {
        return Optional.ofNullable(providers.get(providerId));
    }

    public List<Provider> getAllProviderIds() {
        return providers.keySet().stream().toList();
    }

    public boolean hasProvider(Provider provider) {
        return providers.containsKey(provider);
    }
} 