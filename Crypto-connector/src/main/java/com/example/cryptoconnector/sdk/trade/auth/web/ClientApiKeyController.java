package com.example.cryptoconnector.sdk.trade.auth.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cryptoconnector.sdk.trade.auth.model.CoinbaseApiCredentialsRequest;
import com.example.cryptoconnector.sdk.trade.auth.service.CoinbaseCredentialService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
@Validated
public class ClientApiKeyController {

    private final CoinbaseCredentialService credentialService;

    @PostMapping("save/{clientId}")
    public ResponseEntity<Void> saveCredentials(
            @PathVariable String clientId,
            @Valid @RequestBody CoinbaseApiCredentialsRequest request) {

        credentialService.saveClientSecrets(clientId, new ObjectMapper()
                .convertValue(request, new TypeReference<>() {}));
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{clientId}")
    public ResponseEntity<Map<String, String>> getCredentials(@PathVariable String clientId) {
        return ResponseEntity.ok(credentialService.getClientSecrets(clientId));
    }
}
