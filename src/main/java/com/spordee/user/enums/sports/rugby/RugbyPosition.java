package com.spordee.user.enums.sports.rugby;

public enum RugbyPosition {
    HOOKER("Hooker"),
    LOOSE_HEAD_PROP("Loosehead Prop"),
    TIGHT_HEAD_PROP("Tighthead Prop"),
    SECOND_ROW("Second Row"),
    BLINDSIDE_FLANKER("Blindside Flanker"),
    OPEN_SIDE_FLANKER("Openside Flanker"),
    NUMBER_8("Number 8"),
    SCRUM_HALF("Scrum-Half"),
    FLY_HALF("Fly-Half"),
    INSIDE_CENTRE("Inside Centre"),
    OUTSIDE_CENTRE("Outside Centre"),
    RIGHT_WING("Right Wing"),
    LEFT_WING("Left Wing"),
    FULL_BACK("Full Back");

    private String value;

    RugbyPosition(String value) {
        this.value = value;
    }
}

