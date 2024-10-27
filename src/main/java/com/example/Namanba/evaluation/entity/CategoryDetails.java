package com.example.Namanba.evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryDetails {
    HEAD_TOUCH("head_touch"),
    FACE_TOUCH("face_touch"),
    ARM_TOUCH("arm_touch"),
    HEAD_MOVEMENT("head_movement"),
    BODY_MOVEMENT("body_movement");

    private final String value;
}