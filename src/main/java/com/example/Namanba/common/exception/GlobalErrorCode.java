package com.example.Namanba.common.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    SERVER_ERROR(500, "GLOBAL_500", "서버 내부에서 알 수 없는 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}