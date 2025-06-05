package com.example.cryptoconnector.sdk.trade.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoinbaseApiCredentialsRequest {
    @NotBlank
    private String apiKey;

    @NotBlank
    private String apiSecret;

    private String passphrase;
}