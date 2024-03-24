package com.spordee.user.enums;

import lombok.Getter;

@Getter
public enum RegistrationType {
    REGISTRATION_TYPE_CLUB("CLUB"),
    REGISTRATION_TYPE_PLAYER("PLAYER"),
    REGISTRATION_TYPE_FAN("FAN"),
    REGISTRATION_TYPE_ADVERTISER("ADVERTISER");

    private final String value;

    RegistrationType(String value) {
        this.value = value;
    }
}

