package com.example.Namanba.evaluation.service;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.exception.InterviewErrorCode;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.entity.Evaluation;
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
}
