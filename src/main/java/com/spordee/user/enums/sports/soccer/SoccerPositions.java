package com.spordee.user.enums.sports.soccer;

public enum


SoccerPositions {

    GOALKEEPER("GoalKeeper"),
    SWEEPER("Sweeper"),
    CENTRE_DEFENCE("Centre Defence"),
    RIGHT_BACK("Right Back"),
    LEFT_BACK("Left Back"),
    CENTRE_DEFENSIVE_MIDFIELDER("Centre Defensive Midfielder (CDM)"),
    CENTRE_ATTACKING_MIDFIELDER("Centre Attacking Midfielder (CAM)"),
    RIGHT_MIDFIELD("Right Midfield"),
    LEFT_MIDFIELD("Left Midfield"),
    STRIKER("Striker"),
    LEFT_WING("Left Wing"),
    RIGHT_WING("Right Wing");


    private String value;

    SoccerPositions(String value) {
        this.value = value;
    }
}
