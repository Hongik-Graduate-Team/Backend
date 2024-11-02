package com.example.Namanba.gaze.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GazeErrorCode implements BaseErrorCode {
    INVALID_DIRECTION(500, "GAZE_500_1", "유효하지 않은 방향값입니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
