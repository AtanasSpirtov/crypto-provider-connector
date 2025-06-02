package com.example.cryptoconnector.sdk.trade.web;

import com.example.cryptoconnector.sdk.trade.CoinbaseApiCredentialsRequest;
import com.example.cryptoconnector.sdk.trade.CoinbaseCredentialService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
