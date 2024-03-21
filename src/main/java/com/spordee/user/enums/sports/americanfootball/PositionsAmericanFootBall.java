package com.spordee.user.enums.sports.americanfootball;

public enum PositionsAmericanFootBall {
    QUARTERBACK("Quarterback (QB)"),
    RUNNING_BACK("Running Back (RB)"),
    WIDE_RECEIVER("Wide Receiver (WR)"),
    TIGHT_END("Tight End (TE)"),
    OFFENSIVE_LINEMAN("Offensive Lineman"),
    DEFENSIVE_LINEMAN("Defensive Lineman"),
    LINEBACKER("Linebacker"),
    CORNER_BACK("Cornerback"),
    SAFETY("Safety"),
    KICKER("Kicker (K)"),
    LONG_SNAPPER("Long Snapper (LS)"),
    KICK_RETURNER("Kick Returner (KR)"),
    PUNTER("Punter (P)"),
    PUNT_RETURNER("Punt Returner (PR)"),
    GUNNER("Gunner (G)"),
    PERSONAL_PROTECTOR("Personal Protector (PP)"),
    HOLDER("Holder (H)");

    private String value;

    PositionsAmericanFootBall(String value) {
        this.value = value;
    }
}
