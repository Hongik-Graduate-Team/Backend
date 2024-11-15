package com.example.Namanba.audio.usecase.processor;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
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

@Processor
@RequiredArgsConstructor
public class VoiceVolumeProcessor {
    public AudioEvaluationDto processAudio(File audioFile) {
        try {
            // 오디오 스트림을 생성하여 처리할 준비
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFile.getAbsolutePath(), 44100, 1024, 512);

            // 평균 목소리 크키 반환
            calculateAverageDecibel(audioFile);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 예외 발생 시 null 반환 (예외 처리를 추가할 수 있습니다)
    }

    public double calculateAverageDecibel(File audioFile) throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioInputStream.getFormat();
        long frameLength = audioInputStream.getFrameLength();
        int frameSize = format.getFrameSize();

        byte[] buffer = new byte[frameSize];
        double sum = 0;
        int count = 0;

        try {
            while (audioInputStream.read(buffer) != -1) {
                double rms = calculateRMS(buffer, format);

                if (rms > 0) {  // RMS 값이 0일 때 데시벨 계산을 피함
                    double decibel = 20 * Math.log10(rms);
                    sum += decibel;
                    count++;
                }
            }
        } finally {
            audioInputStream.close();
        }
        double volume=0;

        if(count>0){
            volume = sum / count;
        }

        System.out.println("목소리크기: "+volume);

        return volume; // 카운트가 0이면 0 반환
    }

    private double calculateRMS(byte[] buffer, AudioFormat format) {
        double sum = 0;
        int sampleSizeInBytes = format.getSampleSizeInBits() / 8;

        for (int i = 0; i < buffer.length; i += sampleSizeInBytes) {
            int sample = 0;
            for (int j = 0; j < sampleSizeInBytes; j++) {
                sample |= (buffer[i + j] & 0xFF) << (j * 8);
            }

            float normalizedSample = sample / (float) Math.pow(2, format.getSampleSizeInBits() - 1);
            sum += normalizedSample * normalizedSample;
        }

        return Math.sqrt(sum / (buffer.length / sampleSizeInBytes));
    }
}
