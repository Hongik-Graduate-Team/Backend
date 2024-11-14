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
    private String silenceDurationMessage; // 침묵 시간에 대한 피드백

    @NotNull
    private String voiceVolumeMessage; // 목소리 크게에 대한 피드백

    public static AudioEvaluationDto of(double silenceDuration, String silenceDurationMessage) {
        return AudioEvaluationDto.builder()
                .silenceDuration(silenceDuration)
                .silenceDurationMessage(silenceDurationMessage)
                .build();
    }
}
