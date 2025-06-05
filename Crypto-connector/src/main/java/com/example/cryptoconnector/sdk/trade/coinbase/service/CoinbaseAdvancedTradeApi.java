package com.example.cryptoconnector.sdk.trade.coinbase.service;

import com.coinbase.advanced.client.CoinbaseAdvancedClient;
import com.coinbase.advanced.credentials.CoinbaseAdvancedCredentials;
import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;
import com.example.cryptoconnector.sdk.trade.auth.service.CredentialService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinbaseAdvancedTradeApi {

    private final CredentialService credentialService;

    private CoinbaseAdvancedClient coinbaseAdvancedClient(String clientId) throws JsonProcessingException {
        Map<String, String> credentialsFromVaultSerialized = credentialService.getClientSecrets(clientId, Provider.COINBASE);
        String apiKey = credentialsFromVaultSerialized.get("apiKey");
        String privateKey = credentialsFromVaultSerialized.get("apiSecret");

        CoinbaseAdvancedCredentials credentials = new CoinbaseAdvancedCredentials(apiKey, privateKey);
        return new CoinbaseAdvancedClient(credentials);
    }

    public String nicee(String clientId) {
        try {
            var a = coinbaseAdvancedClient(clientId);
            return "instance";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
