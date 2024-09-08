package com.example.Namanba.common.exception.base;

public interface BaseErrorCode {
    public String getCode(); //에러코드
    public String getMessage(); //메시지
    public int getHttpStatus(); //http 상태코드 정의

}
