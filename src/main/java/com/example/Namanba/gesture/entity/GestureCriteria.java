package com.example.Namanba.gesture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GestureCriteria {
    HEAD_TOUCH(21, 20, 10),
    ARM_TOUCH(21, 20, 10),
    HEAD_MOVEMENT(21, 20, 10),
    BODY_MOVEMENT(11, 10, 5);

    private final int poorCount;
    private final int fairCount;
    private final int excellentCount;

    public GestureScore evaluate(int count) {
        if (count >= poorCount) return GestureScore.POOR; // Poor일 때
        if (count >= fairCount) return GestureScore.FAIR; // Fair일 때
        return GestureScore.EXCELLENT; // Excellent일 때
    }
}

