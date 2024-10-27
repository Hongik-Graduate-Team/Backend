package com.example.Namanba.gesture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GestureCriteria {
    HEAD_TOUCH(4, 2, 1),
    FACE_TOUCH(4, 2, 1),
    ARM_TOUCH(4, 3, 2),
    HEAD_MOVEMENT(4, 3, 2),
    BODY_MOVEMENT(4, 2, 1);

    private final int poorCount;
    private final int fairCount;
    private final int excellentCount;

    public GestureScore evaluate(int count) {
        if (count >= poorCount) return GestureScore.POOR; // Poor일 때
        if (count >= fairCount) return GestureScore.FAIR; // Fair일 때
        return GestureScore.EXCELLENT; // Excellent일 때
    }
}

