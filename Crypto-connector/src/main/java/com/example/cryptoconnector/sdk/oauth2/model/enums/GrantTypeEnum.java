package com.example.cryptoconnector.sdk.oauth2.model.enums;

import lombok.Getter;

@Getter
public enum GrantTypeEnum {
    REFRESH("refresh_token"), AUTHENTICATE("authorization_code");

    private final String value;

    GrantTypeEnum(String value) {
        this.value = value;
    }

}
