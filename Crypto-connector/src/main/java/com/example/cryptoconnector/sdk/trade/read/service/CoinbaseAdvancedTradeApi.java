package com.example.cryptoconnector.sdk.trade.read.service;

import com.coinbase.advanced.client.CoinbaseAdvancedClient;
import com.coinbase.advanced.credentials.CoinbaseAdvancedCredentials;
import com.example.cryptoconnector.sdk.trade.auth.service.CoinbaseCredentialService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinbaseAdvancedTradeApi {

    private final CoinbaseCredentialService credentialService;

    private CoinbaseAdvancedClient coinbaseAdvancedClient(String clientId) throws JsonProcessingException {
        Map<String, String> credentialsFromVaultSerialized = credentialService.getClientSecrets(clientId);
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
