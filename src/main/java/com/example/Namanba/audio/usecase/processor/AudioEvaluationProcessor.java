package com.example.Namanba.audio.usecase.processor;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.SilenceDetector;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import com.example.Namanba.audio.dto.response.AudioEvaluationDto;
import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.repository.EvaluationContentRepository;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.expression.dto.response.ExpressionEvaluationDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import lombok.RequiredArgsConstructor;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Processor
@RequiredArgsConstructor
public class AudioEvaluationProcessor {

    private final EvaluationContentRepository evaluationContentRepository;

    private final EvaluationDomainService evaluationDomainService;

    // 오디오 파일을 처리하는 메서드
    public AudioEvaluationDto processAudio(File audioFile) {
        try {
            // 오디오 스트림을 생성하여 처리할 준비
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);

            // 침묵 구간 계산 및 결과 맵 반환
            Map<String, Object> resultMap = calculateSilenceRatio(dispatcher);

            // Map에서 score와 message를 꺼내서 AudioEvaluationDto 생성
            int score = (int) resultMap.get("score");
            String message = (String) resultMap.get("message");

            return AudioEvaluationDto.of(score, message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 예외 발생 시 null 반환 (예외 처리를 추가할 수 있습니다)
    }


    // 전체 시간과 침묵 시간 비율을 계산하는 메서드
    // 전체 시간과 침묵 시간 비율을 계산하고 Map으로 반환하는 메서드
    private Map<String, Object> calculateSilenceRatio(AudioDispatcher dispatcher) {
        final double[] totalDuration = {0};
        final double[] silenceDuration = {0};
        final double[] previousTimestamp = {0}; // 이전 타임스탬프를 배열로 정의하여 상태 유지

        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                // 전체 시간 기록: 오디오 이벤트의 마지막 타임스탬프 업데이트
                totalDuration[0] = audioEvent.getTimeStamp();

                // 데시벨 계산 (침묵 구간 판별)
                float[] buffer = audioEvent.getFloatBuffer();
                double rms = 0.0;
                for (float sample : buffer) {
                    rms += sample * sample;
                }
                rms = Math.sqrt(rms / buffer.length);
                double decibel = 20 * Math.log10(rms);

                // threshold는 -50dB로 설정 (침묵 기준)
                double threshold = -50.0;
                if (decibel < threshold) {
                    // 침묵 구간의 지속 시간을 누적
                    if (previousTimestamp[0] > 0) {
                        silenceDuration[0] += audioEvent.getTimeStamp() - previousTimestamp[0];
                    }
                }

                // 이전 타임스탬프 업데이트
                previousTimestamp[0] = audioEvent.getTimeStamp();

                return true;
            }

            @Override
            public void processingFinished() {
                // 처리 완료 후 실행되는 코드
            }
        });

        dispatcher.run();  // 전체 시간과 침묵 시간 계산

        System.out.println("침묵: " + silenceDuration[0]);
        System.out.println("전체: " + totalDuration[0]);

        double silenceRate = silenceDuration[0] / totalDuration[0];

        int score = calculateScore(silenceRate);
        String message = createFeedback(score);

        System.out.println("음성 피드백: " + message);

        // Map에 score와 message를 담아 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("score", score);
        resultMap.put("message", message);

        return resultMap;
    }



    private int calculateScore(double silenceRate) {
        if (silenceRate >= 0.5) {
            return 0;
        } else if (silenceRate >= 0.4) {
            return 1;
        } else if (silenceRate >= 0.3) {
            return 2;
        } else if (silenceRate >= 0.2) {
            return 3;
        } else if (silenceRate >= 0.15) {
            return 4;
        } else {
            return 5;
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

        String feedback  = evaluationContentRepository.findByCategoryAndCriteria(Category.SILENCEDURATION, criteria).getMessage();

        return feedback;
    }

    public AudioEvaluationDto createAudioEvaluationDto(double score, String feedback ){
        return AudioEvaluationDto.builder()
                .silenceDuration(score)
                .silenceDurationMessage(feedback)
                .build();
    }


    // 발화 속도 계산 메서드 (분당 단어 수)
    /*
        private double calculateSpeechRate(AudioDispatcher dispatcher) {
        final int[] wordCount = {0};
        final double[] duration = {0};

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 44100, 1024, (pitchDetectionResult, audioEvent) -> {
            if (pitchDetectionResult.getPitch() != -1) {
                wordCount[0]++;
                duration[0] += audioEvent.getTimeStamp();
            }
        }));

        dispatcher.run();  // 발화 속도를 계산
        return (wordCount[0] / (duration[0] / 60));  // 분당 단어 수 (WPM)
    }
     */
    // 데시벨 계산 메서드
    /*
     private double calculateAverageDecibel(AudioDispatcher dispatcher) {
        final double[] sum = {0};
        final int[] count = {0};

        dispatcher.addAudioProcessor((audioEvent) -> {
            float[] buffer = audioEvent.getFloatBuffer();
            double rms = 0.0;
            for (float sample : buffer) {
                rms += sample * sample;
            }
            rms = Math.sqrt(rms / buffer.length);
            double decibel = 20 * Math.log10(rms);
            sum[0] += decibel;
            count[0]++;
        });

        dispatcher.run();  // 디스패처가 실행되면서 오디오 데이터를 처리
        return sum[0] / count[0];
    }
     */
}
