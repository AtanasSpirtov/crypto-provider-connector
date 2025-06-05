package com.example.cryptoconnector.sdk.trade.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.Map;
import java.util.stream.Collectors;

import com.example.cryptoconnector.sdk.oauth2.model.enums.Provider;
import com.example.cryptoconnector.sdk.trade.shared.ClientSecretEncryptionService;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final VaultTemplate vaultTemplate;

    private final ClientSecretEncryptionService encryptionService;

    public void saveClientSecrets(String clientId, Provider provider, Map<String, String> secrets) {
        Map<String, String> encrypted = secrets.entrySet().stream()
                .filter(keyValue -> keyValue.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> encryptionService.encrypt(e.getValue())
                ));
        String path = getPathForClient(clientId, provider);
        vaultTemplate.write(path, Map.of("data", encrypted));
    }

    public Map<String, String> getClientSecrets(String clientId, Provider provider) {
        String path = getPathForClient(clientId, provider);
        VaultResponseSupport<Map> response = vaultTemplate.read(path, Map.class);
        if (response == null || response.getData() == null) return Map.of();

        Map<String, String> encrypted = (Map<String, String>) response.getData().get("data");
        return encrypted.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> encryptionService.decrypt(e.getValue())));
    }

    private static String getPathForClient(String clientId, Provider provider) {
        return String.format("secret/data/clients/%s/provider/%s", clientId, provider.name());
    }

}
