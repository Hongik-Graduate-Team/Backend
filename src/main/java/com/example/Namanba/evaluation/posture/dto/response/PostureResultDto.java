package com.example.Namanba.evaluation.posture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PostureResultDto {
    private double posture;
    private String postureMessage;

    public static PostureResultDto of(double posture, String postureMessage) {
        return PostureResultDto.builder()
                .posture(posture)
                .postureMessage(postureMessage)
                .build();
    }
}
