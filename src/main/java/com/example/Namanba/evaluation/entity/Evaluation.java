package com.example.Namanba.evaluation.entity;

import com.example.Namanba.Interview.entity.Interview;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "evaluation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    private double eyeControl;
    private String eyeControlMessage;

    private double facialExpression;
    private String facialExpressionMessage;

    private double posture;
    private String postureMessage;

    private double voiceVolume;
    private String voiceVolumeMessage;

    private double speechRate;
    private String speechRateMessage;

    private double silenceDuration;
    private String silenceDurationMessage;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
