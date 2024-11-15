package com.example.Namanba.audio.dto.response;

import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AudioEvaluationDto {
    @NotNull
    private double silenceDuration; // 침묵 시간에 대한 점수

    @NotNull
    private double voiceVolume; // 목소리 크기에 대한 점수

    @NotNull
    private double speechRate; // 발화 속도에 대한 점수

    @NotNull
    private String silenceDurationMessage; // 침묵 시간에 대한 피드백

    @NotNull
    private String voiceVolumeMessage; // 목소리 크기에 대한 피드백

    @NotNull
    private String speechRateMessage; // 발화 속도에 대한 피드백

    // 침묵 시간에 대한 점수와 피드백을 위한 of 메서드
    public static AudioEvaluationDto ofSilenceDuration(double silenceDuration, String silenceDurationMessage) {
        return AudioEvaluationDto.builder()
                .silenceDuration(silenceDuration)
                .silenceDurationMessage(silenceDurationMessage)
                .build();
    }

    // 목소리 크기에 대한 점수와 피드백을 위한 of 메서드
    public static AudioEvaluationDto ofVoiceVolume(double voiceVolume, String voiceVolumeMessage) {
        return AudioEvaluationDto.builder()
                .voiceVolume(voiceVolume)
                .voiceVolumeMessage(voiceVolumeMessage)
                .build();
    }

    // 발화 속도에 대한 점수와 피드백을 위한 of 메서드
    public static AudioEvaluationDto ofSpeechRate(double speechRate, String speechRateMessage) {
        return AudioEvaluationDto.builder()
                .speechRate(speechRate)
                .speechRateMessage(speechRateMessage)
                .build();
    }

    // 침묵 시간과 목소리 크기에 대한 점수 및 메시지를 모두 포함한 of 메서드
    public static AudioEvaluationDto of(double silenceDuration, double voiceVolume, String silenceDurationMessage, String voiceVolumeMessage) {
        return AudioEvaluationDto.builder()
                .silenceDuration(silenceDuration)
                .voiceVolume(voiceVolume)
                .silenceDurationMessage(silenceDurationMessage)
                .voiceVolumeMessage(voiceVolumeMessage)
                .build();
    }
}
