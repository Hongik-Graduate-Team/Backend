package com.example.Namanba.common.response;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
public class BaseResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final Boolean isSuccess;
    private final int httpStatus;
    private final String message;

    public static BaseResponse of(Boolean isSuccess, BaseErrorCode code){
        return new BaseResponse(isSuccess, code.getHttpStatus(), code.getMessage());
    }

    public static BaseResponse success(){
        return new BaseResponse(true, 200,"호출에 성공하셨습니다.");

    }
}
