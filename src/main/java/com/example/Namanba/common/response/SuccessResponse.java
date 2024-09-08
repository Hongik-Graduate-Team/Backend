package com.example.Namanba.common.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SuccessResponse<T> extends BaseResponse{
    private final T data;
    public SuccessResponse(T data){
        super(true, 200, "호출에 성공했습니다.");
        this.data = data;
    }

    public static <T> SuccessResponse<T> of(T data){
        return new SuccessResponse<>(data);
    }

    public static <T> SuccessResponse<T> empty(){
        return new SuccessResponse<>(null);
    }
}
