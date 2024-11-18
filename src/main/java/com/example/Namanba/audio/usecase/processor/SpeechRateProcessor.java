package com.example.Namanba.audio.usecase.processor;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.repository.EvaluationContentRepository;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import lombok.RequiredArgsConstructor;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.*;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Processor
@RequiredArgsConstructor
public class SpeechRateProcessor {

    private final EvaluationContentRepository evaluationContentRepository;

    public AudioEvaluationDto processAudio(File audioFile) {
        try {

            // 발화 속도 측정 결과 반환
            Map<String, Object> resultMap = calculateSpeechRate(audioFile);

            int score = (int) resultMap.get("score");
            String message = (String) resultMap.get("message");

            return AudioEvaluationDto.ofSpeechRate(score,message);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 예외 발생 시 null 반환 (예외 처리를 추가할 수 있습니다)
    }

    public Map<String, Object> calculateSpeechRate(File audioFile) {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);

        final int[] wordCount = {0};
        final double[] duration = {0};
        final double[] speakingStartTime = {0};

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 44100, 1024, (pitchDetectionResult, audioEvent) -> {
            if (pitchDetectionResult.getPitch() != -1) {  // 피치가 감지되면
                if (speakingStartTime[0] == 0) {
                    speakingStartTime[0] = audioEvent.getTimeStamp();  // 발화 시작 시간 기록
                }
                wordCount[0]++;  // 피치가 감지되면 단어를 하나 추가
            } else if (speakingStartTime[0] != 0) {  // 피치가 감지되지 않으면 발화가 끝났다고 판단
                duration[0] += (audioEvent.getTimeStamp() - speakingStartTime[0]);  // 발화 구간 시간 더하기
                speakingStartTime[0] = 0;  // 발화 시작 시간 초기화
            }
        }));

        dispatcher.run();  // 발화 속도를 계산

        // 발화 속도 계산 (분당 단어 수)
        double rate = (wordCount[0] / (duration[0] / 60));

        // 과도한 발화 속도 제한 (예: 최대 300 WPM으로 제한)
        if (rate > 300) {
            rate = 300;
        }

        System.out.println("발화 속도: " + rate);

        int score = calculateScore(rate);
        String message = createFeedback(score);

        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("message", message);

        return result;  // 분당 단어 수 (WPM)
    }

    private int calculateScore(double rate) {
        if (rate >= 180) {
            return 5;
        } else if (rate >= 130) {
            return 3;
        } else {
            return 1;
        }
    }

    private String createFeedback(int score){
        String criteria;
        if(score>=4){
            criteria = "Excellent";
        }
        else if (score>=2){
            criteria = "Fair";
        }
        else{
            criteria = "Poor";
        }

        String feedback  = evaluationContentRepository.findByCategoryAndCriteria(Category.SPEECHRATE, criteria).getMessage();

        return feedback;
    }

}
