package com.example.Namanba.gesture.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gesture.dto.request.GestureDataDto;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import com.example.Namanba.gesture.usecase.processor.GestureEvaluationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluateGestureUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final EvaluationDomainService evaluationDomainService;
    private final GestureEvaluationProcessor gestureEvaluationProcessor;

    public void execute(Long interviewId, GestureDataDto gestureData) {
        GestureEvaluationDto gestureEvaluation = gestureEvaluationProcessor.evaluateGesture(gestureData);
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        evaluationDomainService.evaluateGesture(interview,gestureEvaluation);
    }

}
