package com.example.Namanba.gaze.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetGazeEvaluationUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final EvaluationDomainService evaluationDomainService;
    public GazeEvaluationDto execute(Long interviewId) {
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        return evaluationDomainService.getGazeEvaluation(interview);
    }
}
