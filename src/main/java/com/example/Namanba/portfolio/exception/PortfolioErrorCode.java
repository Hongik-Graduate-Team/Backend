package com.example.Namanba.portfolio.exception;


import com.example.Namanba.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfolioErrorCode implements BaseErrorCode {

    POSITION_NOT_FOUND(404, "POSITION_404_1", "해당 포지션을 찾을 수 없습니다."),
    PORTFOLIO_NOT_FOUND(404, "PORTFOLIO_404_1", "해당 포트폴리오를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}
