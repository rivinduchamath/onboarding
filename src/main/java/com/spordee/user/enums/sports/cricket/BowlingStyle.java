package com.spordee.user.enums.sports.cricket;

public enum BowlingStyle {
    RIGHT_ARM_FAST( "Right-Arm Fast"),
    RIGHT_ARM_MEDIUM("Right-Arm Medium"),
    RIGHT_ARM_SLOW("Right-Arm Slow"),
    LEFT_ARM_FAST("Left-Arm Fast"),
    LEFT_ARM_MEDIUM("Left-Arm Medium"),
    LEFT_ARM_SLOW("Left-Arm Slow"),
    OFF_BREAK("Off Break"),
    LEG_BREAK("Leg Break"),
    ORTHODOX("Orthodox"),
    WRIST_SPIN("Wrist Spin"),
    GOOGLY("Googly");

    private  String value;

    BowlingStyle(String value) {
        this.value = value;
    }
}
