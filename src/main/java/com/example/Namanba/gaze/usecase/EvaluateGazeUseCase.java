package com.example.Namanba.gaze.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gaze.usecase.processor.GazeEvaluationProcessor;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class EvaluateGazeUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final EvaluationDomainService evaluationDomainService;
    private final GazeEvaluationProcessor gazeEvaluationProcessor;

    public void execute(Long interviewId, GazeDataDto gazeData) {
        GazeEvaluationDto gazeEvaluation = gazeEvaluationProcessor.evaluateGaze(gazeData);
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        evaluationDomainService.evaluateGaze(interview,gazeEvaluation);
    }

}
