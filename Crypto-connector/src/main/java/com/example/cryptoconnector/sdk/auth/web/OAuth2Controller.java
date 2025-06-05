package com.example.cryptoconnector.sdk.auth.web;

import java.util.List;
import java.util.Optional;

import com.example.cryptoconnector.sdk.auth.model.enums.Provider;
import com.example.cryptoconnector.sdk.auth.provider.OAuth2Provider;
import com.example.cryptoconnector.sdk.auth.service.OAuth2ProviderRegistry;
import com.example.cryptoconnector.sdk.auth.service.GenericOAuth2Service;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Generic OAuth2 controller that works with any OAuth2 provider.
 * Supports multiple providers through the provider registry.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class OAuth2Controller {

  private final GenericOAuth2Service genericOAuth2Service;
  private final OAuth2ProviderRegistry providerRegistry;

  @GetMapping("/{providerId}/login-url")
  public ResponseEntity<String> getLoginUrl(@PathVariable String providerId) {
    Optional<OAuth2Provider> provider = providerRegistry.getProvider(Provider.fromValue(providerId));
    if (provider.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    String loginUrl = genericOAuth2Service.getLoginUrl(provider.get());
    return ResponseEntity.ok(loginUrl);
  }

  @GetMapping("/{providerId}/callback")
  public ResponseEntity<Void> callback(
      @PathVariable String providerId,
      @RequestParam("code") String code,
      @RequestParam(value = "login_identifier", required = false) String loginIdentifier,
      @RequestParam(value = "state", required = false) String state) {

    Optional<OAuth2Provider> provider = providerRegistry.getProvider(Provider.fromValue(providerId));
    if (provider.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    genericOAuth2Service.saveNewClientByCode(provider.get(), code);

    return ResponseEntity.accepted().build();
  }

  @GetMapping("/{providerId}/user/{userId}")
  public ResponseEntity<Object> getUserInfoCached(@PathVariable Provider providerId, @PathVariable String userId) {
    Optional<OAuth2Provider> provider = providerRegistry.getProvider(providerId);
    if (provider.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    try {
      Object userInfo = genericOAuth2Service.getUserInfoCached(provider.get(), userId);
      return ResponseEntity.ok(userInfo);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{providerId}/heartbeat/{userId}")
  public ResponseEntity<Object> pollRefreshUserInfo(@PathVariable Provider providerId, @PathVariable String userId) {
    Optional<OAuth2Provider> provider = providerRegistry.getProvider(providerId);
    if (provider.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    try {
      Object userInfo = genericOAuth2Service.getLatestUserInfo(provider.get(), userId);
      return ResponseEntity.ok(userInfo);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/providers")
  public ResponseEntity<List<Provider>> getAvailableProviders() {
    return ResponseEntity.ok(providerRegistry.getAllProviderIds());
  }
} 