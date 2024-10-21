package com.example.Namanba.evaluation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "evaluation_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EvaluationContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_content_id")
    private Long evaluationContentId;
    private String category; // 제스처, 시선, 표정, 목소리 등
    private String categoryDetails; // 각 카테고리 별로 세부 항목 (없으면 null)
    private String criteria; // 각 카테고리 별 점수, ex) excellent, poor 등
    private String message; // 각 점수 별로 설명 메시지

}
