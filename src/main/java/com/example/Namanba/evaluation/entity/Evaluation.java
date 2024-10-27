package com.example.Namanba.evaluation.entity;

import com.example.Namanba.Interview.entity.Interview;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    @Column(nullable = true)
    private double eyeControl;
    @Column(nullable = true)
    private String eyeControlMessage;
    @Column(nullable = true)
    private Double expression;
    @Column(nullable = true)
    private String expressionMessage;
    @Column(nullable = true)
    private double gesture;
    @Column(nullable = true)
    private String gestureMessage;
    @Column(nullable = true)
    private double voiceVolume;
    @Column(nullable = true)
    private String voiceVolumeMessage;
    @Column(nullable = true)
    private double speechRate;
    @Column(nullable = true)
    private String speechRateMessage;
    @Column(nullable = true)
    private double silenceDuration;
    @Column(nullable = true)
    private String silenceDurationMessage;

    @OneToOne
    @JoinColumn(name = "interview_id", unique = true)
    private Interview interview;

    public void assignGesture(double gesture, String gestureMessage){
        this.gesture = gesture;
        this.gestureMessage = gestureMessage;
    }

    public void assignExpression(double expression, String expressionMessage){
        this.expression = expression;
        this.expressionMessage = expressionMessage;
    }
}