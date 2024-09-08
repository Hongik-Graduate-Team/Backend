package com.example.Namanba.common.response;

import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponse extends BaseResponse{
    private final int httpStatus;

    @Builder
    public ErrorResponse(BaseErrorCode errorCode){
        super(false,errorCode.getHttpStatus(), errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}
