package com.spordee.user.enums;

import lombok.Getter;

@Getter
public enum CommonMessages {
    REQUEST_SUCCESS("Success Request"),
    BAD_CREDENTIALS("Bad Credentials"),
    REQUEST_FAIL("Fail Request"),
    REQUEST_NO_LONGER_USED("No Longer Used"),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR"),
    REQUEST_CONFLICT("CONFLICT Request");

    private final String code;

    CommonMessages(String  code) {
        this.code = code;
    }

}
