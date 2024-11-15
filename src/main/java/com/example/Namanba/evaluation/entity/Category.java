package com.example.Namanba.evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    GESTURE("gesture"),
    GAZE("gaze"),
    EXPRESSION("expression"),

    SILENCEDURATION("silenceDuration"),

    VOICEVOLUME("voiceVolume");

    private final String value;
}
