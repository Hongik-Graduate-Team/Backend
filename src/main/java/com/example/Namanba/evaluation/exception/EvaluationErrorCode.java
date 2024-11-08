package com.example.Namanba.evaluation.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EvaluationErrorCode implements BaseErrorCode {

    EVALUATION_NOT_FOUND(404, "404_EVALUATION_1","평가를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND(404, "404_EVALUATION_2", "평가 기준 메시지를 찾을 수 없습니다.."),

    AUDIO_NOT_EXIST(404,"404_EVALUATION_3","음성 파일이 비어 있습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}
