package com.example.Namanba.Interview.entity;

import com.example.Namanba.evaluation.entity.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvaluationStatus {
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String message;

    //TODO: 추후 5를 6으로 변경
    public static EvaluationStatus from(Evaluation evaluation) {
        if (evaluation == null || evaluation.getSuccess() < 5 ){
            return IN_PROGRESS;
        }
        return COMPLETED;
    }
}
