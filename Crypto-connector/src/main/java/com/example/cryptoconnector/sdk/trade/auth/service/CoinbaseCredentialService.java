package com.example.cryptoconnector.sdk.trade.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.Map;
import java.util.stream.Collectors;

import com.example.cryptoconnector.sdk.trade.shared.ClientSecretEncryptionService;

@Service
@RequiredArgsConstructor
public class CoinbaseCredentialService {

    private final VaultTemplate vaultTemplate;

    private final ClientSecretEncryptionService encryptionService;

    public void saveClientSecrets(String clientId, Map<String, String> secrets) {
        Map<String, String> encrypted = secrets.entrySet().stream()
                .filter(keyValue -> keyValue.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> encryptionService.encrypt(e.getValue())
                ));
        String path = getPathForClient(clientId);
        vaultTemplate.write(path, Map.of("data", encrypted));
    }

    public Map<String, String> getClientSecrets(String clientId) {
        String path = getPathForClient(clientId);
        VaultResponseSupport<Map> response = vaultTemplate.read(path, Map.class);
        if (response == null || response.getData() == null) return Map.of();

        Map<String, String> encrypted = (Map<String, String>) response.getData().get("data");
        return encrypted.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> encryptionService.decrypt(e.getValue())));
    }

    private static String getPathForClient(String clientId) {
        return String.format("secret/data/clients/%s", clientId);
    }

}
