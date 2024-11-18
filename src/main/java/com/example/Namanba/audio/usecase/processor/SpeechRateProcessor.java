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

    public AudioEvaluationDto processAudio(File audioFile) {
        try {
            // 오디오 스트림을 생성하여 처리할 준비
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);

            //calculateSpeechRate(dispatcher);


            //return AudioEvaluationDto.ofVoiceVolume(score,message);

            calculateSpeechRate(audioFile);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 예외 발생 시 null 반환 (예외 처리를 추가할 수 있습니다)
    }

    public double calculateSpeechRate(File audioFile) {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);


        final int[] wordCount = {0};
        final double[] duration = {0};

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 44100, 1024, (pitchDetectionResult, audioEvent) -> {
            if (pitchDetectionResult.getPitch() != -1) {
                wordCount[0]++;
                duration[0] += audioEvent.getTimeStamp();
            }
        }));

        dispatcher.run();  // 발화 속도를 계산

        double rate = (wordCount[0] / (duration[0] / 60));

        System.out.println("발화 속도: "+rate);
        return rate;  // 분당 단어 수 (WPM)
    }

//    private final Logger logger = LoggerFactory.getLogger(SpeechRateProcessor.class);
//
//    public String transcribe(MultipartFile audioFile) throws IOException {
//        if (audioFile.isEmpty()) {
//            throw new IOException("Required part 'audioFile' is not present.");
//        }
//
//        // 오디오 파일을 byte array로 decode
//        byte[] audioBytes = audioFile.getBytes();
//
//        // 클라이언트 인스턴스화
//        try (SpeechClient speechClient = SpeechClient.create()) {
//            // 오디오 객체 생성
//            ByteString audioData = ByteString.copyFrom(audioBytes);
//            RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
//                    .setContent(audioData)
//                    .build();
//
//            // 설정 객체 생성
//            RecognitionConfig recognitionConfig =
//                    RecognitionConfig.newBuilder()
//                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)  // LINEAR16으로 설정
//                            .setSampleRateHertz(48000)  // WAV 파일의 샘플링 속도와 맞추세요.
//                            .setLanguageCode("ko-KR")
//                            .build();
//
//            // 오디오-텍스트 변환 수행
//            RecognizeResponse response = speechClient.recognize(recognitionConfig, recognitionAudio);
//            List<SpeechRecognitionResult> results = response.getResultsList();
//
//            if (!results.isEmpty()) {
//                // 주어진 말 뭉치에 대해 여러 가능한 스크립트를 제공. 0번(가장 가능성 있는)을 사용한다.
//                SpeechRecognitionResult result = results.get(0);
//                return result.getAlternatives(0).getTranscript();
//            } else {
//                logger.error("No transcription result found");
//                return "";
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }



}
