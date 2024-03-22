package com.spordee.user.enums.sports.hockey;

public enum HockeyPosition {
    GOALIE("Goalie"),
    DEFENCE_RIGHT("Defense Right"),
    DEFENCE_LEFT("Defense Left"),
    CENTRE("Center"),
    WINGER_RIGHT("Winger Right"),
    WINGER_LEFT("Winger Left");

    private String value;

    HockeyPosition(String value) {
        this.value = value;
    }
}
