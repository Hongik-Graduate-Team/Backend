package com.example.Namanba.audio.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import com.example.Namanba.audio.usecase.processor.SilenceDurationProcessor;
import com.example.Namanba.audio.usecase.processor.SpeechRateProcessor;
import com.example.Namanba.audio.usecase.processor.VoiceVolumeProcessor;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import lombok.RequiredArgsConstructor;

import java.io.File;

@UseCase
@RequiredArgsConstructor
public class EvaluateAudioUseCase {
    private final SilenceDurationProcessor silenceDurationProcessor;

    private final VoiceVolumeProcessor voiceVolumeProcessor;

    private final SpeechRateProcessor speechRateProcessor;

    private final EvaluationDomainService evaluationDomainService;

    private final InterviewAdaptor interviewAdaptor;

    public void execute(Long interviewId, File audioFile) {
        AudioEvaluationDto audioEvaluation = silenceDurationProcessor.processAudio(audioFile);
        AudioEvaluationDto audioEvaluationTwo = voiceVolumeProcessor.processAudio(audioFile);
        AudioEvaluationDto audioEvaluationThree = speechRateProcessor.processAudio(audioFile);
        Interview interview = interviewAdaptor.findByInterviewId(interviewId);
        // 침묵 시간 측정 함수 호출
        evaluationDomainService.evaluateSilenceDuration(interview, audioEvaluation);
        // 목소리 크기 측정 함수 호출
        evaluationDomainService.evaluateVoiceVolume(interview,audioEvaluationTwo);
        // 발화 속도 크키 측정 함수 호출
        evaluationDomainService.evaluateSpeechRate(interview,audioEvaluationThree);
        /*
        1. 음성파일을 받아와서 침묵 시간을 계산하여 (점수, 피드백) dto를 생성한다.
        2. 인터뷰를 찾는다
        3. 인터뷰에 연결된 평가 테이블에 침묵 시간 관련 정보를 저장한다.
         */
    }
}
