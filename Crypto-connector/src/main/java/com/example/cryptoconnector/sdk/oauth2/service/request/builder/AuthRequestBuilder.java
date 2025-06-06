package com.example.cryptoconnector.sdk.oauth2.service.request.builder;

import java.util.Map;

public interface AuthRequestBuilder {
  Map<String, String> buildAuthCodeRequest(String code);
  Map<String, String> buildRefreshRequest(String refreshToken);
  String buildBearerHeader(String token);
  String buildLoginUrl(String state);
}
