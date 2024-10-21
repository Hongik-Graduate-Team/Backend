package com.example.Namanba.evaluation.expression.service;

import com.example.Namanba.evaluation.expression.dto.ExpressionDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpressionService {

    // 사용자의 면접 중 표정 비율을 계산하여 DB에 정보를 저장하는 함수
    public void evalExpression(ExpressionDataDto expressionDataDto){

        int negative = expressionDataDto.getSad()
                + expressionDataDto.getAngry()
                + expressionDataDto.getFearful()
                + expressionDataDto.getDisgusted()
                + expressionDataDto.getSurprised();//부정적인 감정 5가지를 더함
        int neutral = expressionDataDto.getNeutral(); //무표정
        int positive = expressionDataDto.getHappy(); //긍정적
        int totalFrames = expressionDataDto.getTotalFrames();//총 프레임 수

        // 비율 계산
        double positiveRatio = (double) positive / totalFrames * 100;
        double negativeRatio = (double) negative / totalFrames * 100;

        // interviewID를 가져와서 그 테이블의 얼굴 표정 속서에 값을 집어 넣음
        // Evaluation 테이블에 들어가는 피드백 문장은 EvaluationContent 테이블의 상 중 하로 나뉜 문장들을 조합해서 나타냄

        // 점수 매기기
        int score = calculateScore(positiveRatio, negativeRatio);

        System.out.println("최종 점수: " + score);
    }

    private int calculateScore(double positiveRatio, double negativeRatio){
        if (positiveRatio >= 60 && negativeRatio<=10){
            return 5;
        }
        else if(positiveRatio >= 60 && negativeRatio<=20){
            return 4;
        }
        else if(positiveRatio >= 50 && negativeRatio>=10 && negativeRatio<=20){
            return 3;
        }
        else if(positiveRatio >= 40 && negativeRatio>=20 && negativeRatio<=40){
            return 2;
        }
        else if(positiveRatio >= 30 && negativeRatio>=30 ){
            return 1;
        }
        else if(positiveRatio < 30 || negativeRatio>=70 ){
            return 0;
        }
        else{
            return 0; //기본 점수
        }
    }
}
