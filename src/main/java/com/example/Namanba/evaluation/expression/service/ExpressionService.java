package com.example.Namanba.evaluation.expression.service;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.repository.InterviewRepository;
import com.example.Namanba.evaluation.entity.Evaluation;
import com.example.Namanba.evaluation.entity.EvaluationContent;
import com.example.Namanba.evaluation.expression.dto.ExpressionDataDto;
import com.example.Namanba.evaluation.repository.EvaluationContentRepository;
import com.example.Namanba.evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpressionService {

    private final EvaluationContentRepository evaluationContentRepository;

    private final EvaluationRepository evaluationRepository;

    private final InterviewRepository interviewRepository;


    // 사용자의 면접 중 표정 비율을 계산하여 DB에 정보를 저장하는 함수
    public void evalExpression(Long interviewId,ExpressionDataDto expressionDataDto){

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

        int score = calculateScore(positiveRatio, negativeRatio);

        String feedback = createFeedback(score);

        updateFacialExpressionFeedback(interviewId, feedback);

        System.out.println("최종 점수: " + score);

        System.out.println("최종 피드백: " + feedback);
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

    private String createFeedback(int score){
        String criteria;
        if(score>=4){
            criteria = "Excellent";
        }
        else if (score>=2){
            criteria = "Fair";
        }
        else{
            criteria = "Poor";
        }

        String feedback  = evaluationContentRepository.findByCategoryAndCriteria("expression", criteria).getMessage();

        return feedback;
    }

    public void updateFacialExpressionFeedback(Long interviewId, String feedback) {
        Interview interview = interviewRepository.findByInterviewId(interviewId);
        // 인터뷰 ID로 Evaluation을 검색
        Evaluation evaluation = evaluationRepository.findByInterview(interview);

        // Evaluation이 없으면 새로 생성
        if (evaluation == null) {
            evaluation = new Evaluation();

            // interviewId를 사용하여 이미 존재하는 Interview 객체를 가져옴
            //Interview interview = interviewRepository.findByInterviewId(interviewId);

            // Interview가 존재하지 않을 경우 예외 처리
            if (interview == null) {
                throw new RuntimeException("Interview not found");
            }

            evaluation.setInterview(interview); // 기존 Interview 설정
        }

        // 얼굴 표정 피드백 설정
        evaluation.setFacialExpressionScore(feedback); // 평가 테이블에 얼굴 표정 피드백 문장을 삽입한다.

        // Evaluation 저장
        evaluationRepository.save(evaluation);
    }
}
