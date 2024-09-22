package com.example.Namanba.Interview.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewErrorCode implements BaseErrorCode {

    BASIC_QUESTION_NOT_FOUND(404, "404_BASIC_QUESTION_1", "해당 포지션에 대한 기본 면접 질문을 찾을 수 없습니다.");
    private final int httpStatus;
    private final String code;
    private final String message;

}
