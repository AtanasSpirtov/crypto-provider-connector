package com.example.cryptoconnector.sdk.auth.login;

import com.example.cryptoconnector.sdk.auth.CoinbaseAuthService;
import com.example.cryptoconnector.sdk.auth.model.CoinbaseUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CoinbaseAuthController {

    private final CoinbaseAuthService coinbaseAuthService;

    @GetMapping("/login-url")
    public String getLoginUrl() {
        return coinbaseAuthService.getCoinbaseLoginUrl();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @RequestParam("login_identifier") String loginIdentifier
    ) {
        coinbaseAuthService.saveNewClientByCodeInCache(code);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/user/{coinbaseUserId}")
    public CoinbaseUserInfo getUserInfoCached(@PathVariable String coinbaseUserId) {
        return coinbaseAuthService.getUserInfoCached(coinbaseUserId);
    }

    @GetMapping("/heartbeat/{coinbaseUserId}")
    public CoinbaseUserInfo pollRefreshUserInfo(@PathVariable String coinbaseUserId) {
        return coinbaseAuthService.getLatestUserInfo(coinbaseUserId);
    }
}

