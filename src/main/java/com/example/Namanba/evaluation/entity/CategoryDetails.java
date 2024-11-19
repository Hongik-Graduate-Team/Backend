package com.example.Namanba.evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryDetails {
    HEAD_TOUCH("head_touch"),
    ARM_TOUCH("arm_touch"),
    HEAD_MOVEMENT("head_movement"),
    BODY_MOVEMENT("body_movement"),

    GAZE_DIRECTION("gaze_direction"),
    GAZE_STABILITY("gaze_stability");

    private final String value;
}