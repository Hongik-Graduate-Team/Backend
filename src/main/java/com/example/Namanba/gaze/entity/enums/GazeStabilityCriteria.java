package com.example.Namanba.gaze.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 숫자가 클수록 좋지 않은 데이터
@AllArgsConstructor
@Getter
public enum GazeStabilityCriteria {
    VERY_STABLE(2.5, 0.0, 0.002),
    STABLE(2.0, 0.002, 0.004),
    SLIGHTLY_UNSTABLE(1.5, 0.004, 0.006),
    UNSTABLE(1.0, 0.006, 0.008),
    VERY_UNSTABLE(0.5, 0.008, 0.01),
    UNACCEPTABLE(0.0, 0.01, Double.MAX_VALUE);

    private final double score;
    private final double min;
    private final double max;

    public static GazeStabilityCriteria evaluate(double stabilityScore){
        for(GazeStabilityCriteria criteria : GazeStabilityCriteria.values()){
            if(stabilityScore > criteria.min && stabilityScore <= criteria.max){
                return criteria;
            }
        } return UNACCEPTABLE;
    }
}
