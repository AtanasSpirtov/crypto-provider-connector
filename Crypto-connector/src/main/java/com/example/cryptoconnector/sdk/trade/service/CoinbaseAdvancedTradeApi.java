package com.example.cryptoconnector.sdk.trade.service;

import com.coinbase.advanced.client.CoinbaseAdvancedClient;
import com.coinbase.advanced.credentials.CoinbaseAdvancedCredentials;
import com.example.cryptoconnector.sdk.trade.CoinbaseCredentialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
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
            return "Kurec";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
