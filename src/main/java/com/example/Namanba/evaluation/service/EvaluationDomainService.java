package com.example.Namanba.evaluation.service;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.exception.InterviewErrorCode;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.entity.Evaluation;
import com.example.Namanba.expression.dto.response.ExpressionEvaluationDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationDomainService {
    private final EvaluationAdaptor evaluationAdaptor;
    private final InterviewAdaptor interviewAdaptor;
    @Transactional
    public void createEvaluation(Interview interview){
        if (interviewAdaptor.existsByInterview(interview.getInterviewId())){
            Evaluation evaluation = Evaluation.builder()
                    .interview(interview)
                    .build();
            evaluationAdaptor.save(evaluation);
        } else throw new BaseException(InterviewErrorCode.INTERVIEW_NOT_FOUND);
    }
    @Transactional
    public void evaluateGesture(Interview interview, GestureEvaluationDto gestureEvaluation){
        Evaluation evaluation = evaluationAdaptor.findByInterview(interview);
        evaluation.assignGesture(gestureEvaluation.getGesture(), gestureEvaluation.getGestureMessage());
        evaluationAdaptor.save(evaluation);
    }

    @Transactional(readOnly = true)
    public GestureEvaluationDto getGestureEvaluation(Interview interview) {
        Evaluation evaluation = evaluationAdaptor.findByInterview(interview);
        return GestureEvaluationDto.builder()
                .gestureMessage(evaluation.getGestureMessage())
                .gesture(evaluation.getGesture())
                .build();
    }

    @Transactional
    public void evaluateGaze(Interview interview, GazeEvaluationDto gazeEvaluation){
        Evaluation evaluation = evaluationAdaptor.findByInterview(interview);
        evaluation.assignGaze(gazeEvaluation.getGaze(), gazeEvaluation.getGazeMessage());
        evaluationAdaptor.save(evaluation);
    }

    @Transactional(readOnly = true)
    public GazeEvaluationDto getGazeEvaluation(Interview interview){
        Evaluation evaluation = evaluationAdaptor.findByInterview(interview);
        return GazeEvaluationDto.builder()
                .gazeMessage(evaluation.getGazeMessage())
                .gaze(evaluation.getGaze())
                .build();
    }

    @Transactional
    public void evaluateExpression(Interview interview, ExpressionEvaluationDto expressionEvaluationDto){
        Evaluation evaluation = evaluationAdaptor.findByInterview(interview);
        // expression 값을 검증하고 기본값 설정
        // expression 값을 검증하고 기본값 설정
        double expressionValue = (expressionEvaluationDto.getExpression() != null)
                ? expressionEvaluationDto.getExpression()
                : 0.0; // null일 경우 0.0로 설정

        evaluation.assignExpression(expressionValue, expressionEvaluationDto.getExpressionMessage());
        evaluationAdaptor.save(evaluation);
    }

}
