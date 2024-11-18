package com.example.Namanba.evaluation.entity;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Evaluation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    @Column(nullable = true)
    private Double gaze;
    @Column(nullable = true)
    private String gazeMessage;

    @Column(nullable = true)
    private Double expression;
    @Column(nullable = true)
    private String expressionMessage;

    @Column(nullable = true)
    private Double gesture;
    @Column(nullable = true)
    private String gestureMessage;

    @Column(nullable = true)
    private Double voiceVolume;
    @Column(nullable = true)
    private String voiceVolumeMessage;

    @Column(nullable = true)
    private Double speechRate;
    @Column(nullable = true)
    private String speechRateMessage;

    @Column(nullable = true)
    private Double silenceDuration;
    @Column(nullable = true)
    private String silenceDurationMessage;

    private int success = 0;  // 메시지 삽입 횟수, 현재 최대 값 6

    @OneToOne
    @JoinColumn(name = "interview_id", unique = true)
    private Interview interview;

    public void assignGesture(double gesture, String gestureMessage) {
        this.gesture = gesture;
        this.gestureMessage = gestureMessage;
        this.success++;
    }

    public void assignGaze(double gaze, String gazeMessage) {
        this.gaze = gaze;
        this.gazeMessage = gazeMessage;
        this.success++;
    }

    public void assignExpression(double expression, String expressionMessage) {
        this.expression = expression;
        this.expressionMessage = expressionMessage;
        this.success++;
    }

    public void assignSilenceDuration(double silenceDuration, String silenceDurationMessage){
        this.silenceDuration = silenceDuration;
        this.silenceDurationMessage = silenceDurationMessage;
        this.success++;
    }

    public void assignVoiceVolume(double voiceVolume, String voiceVolumeMessage){
        this.voiceVolume = voiceVolume;
        this.voiceVolumeMessage = voiceVolumeMessage;
        this.success++;
    }
}