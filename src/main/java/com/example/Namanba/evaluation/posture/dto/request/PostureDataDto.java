package com.example.Namanba.evaluation.posture.dto.request;

import lombok.Getter;

// 프론트로부터 사용자 자세 평가에 대한 점수를 받는 dto (6개의 기준)
@Getter
public class PostureDataDto {
    private int headTouch;        // 머리를 만지는 횟수
    private int faceTouch;        // 얼굴에 손을 대는 횟수
    private int excessiveArmMovement;  // 과도한 팔 움직임 횟수
    private int headMovement;     // 고개를 까닥이는 + 얼굴 회전 횟수
    private int excessiveBodyMovement; // 과도한 몸의 움직임 횟수
}
