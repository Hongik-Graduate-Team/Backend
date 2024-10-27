package com.example.Namanba.gesture.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGestureEvaluationUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final EvaluationDomainService evaluationDomainService;

    public GestureEvaluationDto execute(Long interviewId) {
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        return evaluationDomainService.getGestureEvaluation(interview);
    }

}
