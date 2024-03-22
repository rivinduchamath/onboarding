package com.spordee.user.util;

import lombok.Getter;

@Getter
public enum StatusCodes {

    CODE_SUCCESS(200),
    CODE_NON_AUTHORITATIVE_INFORMATION(203),
    CODE_NO_LONGER_USED(306),
    CODE_BAD_REQUEST(400),
    CODE_UNAUTHORIZED(401),
    CODE_FORBIDDEN(403),
    CODE_NOT_ACCEPTABLE(406),
    CODE_CONFLICT(409),
    CODE_EXPECTATION_FAILED(417),
    CODE_TOO_MANY_REQUESTS(429),
    CODE_INTERNAL_SERVER_ERROR(500);


    private final int code;

    StatusCodes(int code) {
        this.code = code;
    }

}
