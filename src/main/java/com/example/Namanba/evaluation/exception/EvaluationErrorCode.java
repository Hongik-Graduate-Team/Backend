package com.example.Namanba.evaluation.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EvaluationErrorCode implements BaseErrorCode {

    EVALUATION_NOT_FOUND(404, "404_EVALUATION_1","평가를 찾을 수 없습니다.");
    private final int httpStatus;
    private final String code;
    private final String message;

}
