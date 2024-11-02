package com.example.Namanba.gaze.dto.request;

import com.example.Namanba.gaze.entity.Direction;
import lombok.Getter;

@Getter
public class GazeDataDto {
    private Direction direction; // 시선 위치 데이터
    private double stabilityScore; // 시선 안정성 데이터
}
