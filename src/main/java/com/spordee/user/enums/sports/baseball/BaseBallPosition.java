package com.spordee.user.enums.sports.baseball;

public enum BaseBallPosition {
    PITCHER("Pitcher"),
    CATCHER("Catcher"),
    FIRST_BASE("First Base"),
    SECOND_BASE("Second Base"),
    THIRD_BASE("Third Base"),
    SHORT_STOP("Shortstop"),
    LEFT_FIELD("Left Field"),
    RIGHT_FIELD("Right Field"),
    CENTRE_FIELD("Centre Field");


    private String value;

    BaseBallPosition(String value) {
        this.value = value;
    }
}
