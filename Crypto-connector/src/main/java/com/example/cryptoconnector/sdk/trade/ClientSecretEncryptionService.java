package com.example.cryptoconnector.sdk.trade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTransitOperations;

@Service
@RequiredArgsConstructor
public class ClientSecretEncryptionService {

    private final VaultTransitOperations transitOperations;

    private final String keyName = "client-encryption";

    public String encrypt(String plaintext) {
        return transitOperations.encrypt(keyName, plaintext);
    }

    public String decrypt(String ciphertext) {
        return transitOperations.decrypt(keyName, ciphertext);
    }
}
