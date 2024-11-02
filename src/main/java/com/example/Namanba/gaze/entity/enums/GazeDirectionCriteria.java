package com.example.Namanba.gaze.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 숫자가 클수록 좋은 데이터
@AllArgsConstructor
@Getter
public enum GazeDirectionCriteria {
    VERY_GOOD(2.5, 0.9, 1.0),
    GOOD(2.0, 0.7, 0.9),
    FAIR(1.5, 0.6, 0.7),
    POOR(1.0, 0.5, 0.6),
    VERY_POOR(0.5, 0.4, 0.5),
    UNSATISFACTORY(0.0, 0.0, 0.4);

    private final double score;
    private final double min;
    private final double max;

    public static GazeDirectionCriteria evaluate(double centerP){
        for (GazeDirectionCriteria criteria : GazeDirectionCriteria.values()){
            if(centerP > criteria.min && centerP <= criteria.max){
                return criteria;
            }
        } return UNSATISFACTORY;
    }
}
