package com.example.Namanba.audio.usecase;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class EvaluateAudioUserCase {

    public void execute(Long interviewId, GazeDataDto gazeData) {
//        GazeEvaluationDto gazeEvaluation = gazeEvaluationProcessor.evaluateGaze(gazeData);
//        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
//        evaluationDomainService.evaluateGaze(interview,gazeEvaluation);
    }
}
