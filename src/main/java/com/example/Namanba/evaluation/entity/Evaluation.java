package com.example.Namanba.evaluation.entity;

import com.example.Namanba.Interview.entity.Interview;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    private String eyeControlScore;
    private String facialExpressionScore;
    private String gestureScore;
    private String voiceVolumeScore;
    private String speechRateScore;
    private String silenceDurationScore;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
