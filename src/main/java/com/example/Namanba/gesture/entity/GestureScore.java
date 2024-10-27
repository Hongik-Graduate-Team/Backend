package com.example.Namanba.gesture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GestureScore {
    EXCELLENT(1.0),
    FAIR(0.5),
    POOR(0.0);

    private final double score;
}
