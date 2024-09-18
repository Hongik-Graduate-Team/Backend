package com.example.Namanba.common.exception;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import com.example.Namanba.common.exception.base.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends BaseException {

    private final HttpStatus httpStatus;
    private final String message;
    private final String detail;

    // HttpStatus와 메시지를 직접 받는 생성자
    public CustomException(HttpStatus httpStatus, String message) {
        super(null); // BaseErrorCode는 사용하지 않으므로 null로 전달
        this.httpStatus = httpStatus;
        this.message = message;
        this.detail = null; // 디테일이 없을 경우 null로 설정
    }
}

