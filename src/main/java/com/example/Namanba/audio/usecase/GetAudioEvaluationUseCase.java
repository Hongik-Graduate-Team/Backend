package com.example.Namanba.audio.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetAudioEvaluationUseCase {

    private final InterviewAdaptor interviewAdaptor;

    private final EvaluationDomainService evaluationDomainService;

    public AudioEvaluationDto execute(Long interviewId){
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        return evaluationDomainService.getAudioEvaluation(interview);
    }

}
