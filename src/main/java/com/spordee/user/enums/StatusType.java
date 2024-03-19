package com.spordee.user.enums;


import lombok.Getter;

@Getter
public enum StatusType {
    STATUS_SUCCESS("Success"),
    STATUS_FAIL("Fail"),
    STATUS_PENDING("Pending");

    private final String value;

    StatusType(String value) {
        this.value = value;
    }

}
