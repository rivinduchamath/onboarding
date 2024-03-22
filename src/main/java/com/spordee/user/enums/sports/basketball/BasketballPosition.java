package com.spordee.user.enums.sports.basketball;

public enum BasketballPosition {

    POINT_GUARD("Point Guard"),
    SHOOTING_GUARD("Shooting Guard"),
    CENTRE("Center"),
    POWER_FORWARD("Power Forward"),
    SMALL_FORWARD("Small Forward");

    private String value;

    BasketballPosition(String value) {
        this.value = value;
    }
}
