package com.example.Namanba.audio.usecase.processor;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.SilenceDetector;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import com.example.Namanba.common.annotation.Processor;
import lombok.RequiredArgsConstructor;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.InputStream;
@Processor
@RequiredArgsConstructor
public class AudioEvaluationProcessor {

    // 오디오 파일을 처리하는 메서드
    public void processAudio(File audioFile) {
        try {
            // 오디오 스트림을 생성하여 처리할 준비
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);

            // 1. 평균 데시벨 계산
            //double averageDecibel = calculateAverageDecibel(dispatcher);

            // 2. 침묵 구간 계산
            double averageSilence = calculateSilenceRatio(dispatcher);

            // 3. 발화 속도 계산
            //double speechRate = calculateSpeechRate(dispatcher);

            // 결과 출력
            //System.out.println("평균 데시벨: " + averageDecibel + " dB");
            System.out.println("침묵 구간: " + averageSilence + "초");
            //System.out.println("발화 속도: " + speechRate + " WPM");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    // 전체 시간과 침묵 시간 비율을 계산하는 메서드
    private double calculateSilenceRatio(AudioDispatcher dispatcher) {
        final double[] totalDuration = {0};
        final double[] silenceDuration = {0};

        // 오디오 이벤트에서 전체 시간과 침묵 시간을 계산
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                double previousTimestamp = 0;
                // 전체 시간 기록: 오디오 이벤트의 마지막 타임스탬프 업데이트
                if (totalDuration[0] == 0) {
                    totalDuration[0] = audioEvent.getTimeStamp(); // 첫 번째 이벤트에서 전체 시간 초기화
                } else {
                    totalDuration[0] = audioEvent.getTimeStamp(); // 마지막 이벤트에서 전체 시간 업데이트
                }

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
                    if (previousTimestamp > 0) {
                        silenceDuration[0] += audioEvent.getTimeStamp() - previousTimestamp;
                    }
                }

                // 이전 타임스탬프 업데이트
                previousTimestamp = audioEvent.getTimeStamp();

                return true;
            }

            @Override
            public void processingFinished() {
                // 처리 완료 후 실행되는 코드 (필요시)
            }
        });

        dispatcher.run();  // 전체 시간과 침묵 시간 계산

        System.out.println("침묵"+silenceDuration[0]);
        System.out.println("전체"+totalDuration[0]);
        // 침묵 비율 계산
        return silenceDuration[0] / totalDuration[0];
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

}
