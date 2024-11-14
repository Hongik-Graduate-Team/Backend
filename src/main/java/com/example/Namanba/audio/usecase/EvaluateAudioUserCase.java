package com.example.Namanba.audio.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import com.example.Namanba.audio.usecase.processor.AudioEvaluationProcessor;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@UseCase
@RequiredArgsConstructor
public class EvaluateAudioUserCase {
    private final AudioEvaluationProcessor audioEvaluationProcessor;

    private final EvaluationDomainService evaluationDomainService;

    private final InterviewAdaptor interviewAdaptor;

    public void execute(Long interviewId, File audioFile) {
        AudioEvaluationDto audioEvaluation = audioEvaluationProcessor.processAudio(audioFile);
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        evaluationDomainService.evaluateSilenceDuration(interview, audioEvaluation);
        /*
        1. 음성파일을 받아와서 침묵 시간을 계산하여 (점수, 피드백) dto를 생성한다.
        2. 인터뷰를 찾는다
        3. 인터뷰에 연결된 평가 테이블에 침묵 시간 관련 정보를 저장한다.
         */
    }
}
