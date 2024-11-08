package com.example.Namanba.audio.dto.response;

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
    private String silenceDurationMessage; // 침묵 시간에 대한 피드백
}
