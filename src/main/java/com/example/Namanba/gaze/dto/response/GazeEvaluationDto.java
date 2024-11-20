package com.example.Namanba.gaze.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GazeEvaluationDto {
    @NotNull
    private Double gaze;
    @NotNull
    private String gazeMessage;

    public static GazeEvaluationDto of(double gaze, String gazeMessage) {
        return GazeEvaluationDto.builder()
                .gaze(gaze)
                .gazeMessage(gazeMessage)
                .build();
    }
}
