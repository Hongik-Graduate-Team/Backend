package com.example.Namanba.user.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(404, "404_USER_1", "유저를 찾을 수 없습니다.");
    private final int httpStatus;
    private final String code;
    private final String message;

}
