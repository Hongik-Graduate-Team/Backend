package com.example.Namanba.evaluation.expression.dto;

import lombok.Getter;

@Getter
public class ExpressionDataDto {
    private int neutral;   // 무표정 감정 카운트
    private int happy;     // 행복 감정 카운트
    private int sad;       // 슬픈 감정 카운트
    private int angry;     // 분노 감정 카운트
    private int fearful;   // 두려움 감정 카운트
    private int disgusted; // 혐오 감정 카운트
    private int surprised;  // 놀람 감정 카운트
    private int totalFrames; // 총 프레임 수

}
