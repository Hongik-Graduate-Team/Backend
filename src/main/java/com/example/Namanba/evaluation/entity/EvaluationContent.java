package com.example.Namanba.evaluation.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@Table(name = "evaluation_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EvaluationContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_content_id")
    private Long evaluationContentId;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private Category category; // 제스처, 시선, 표정, 목소리 등

    @Nullable
    @Column
    @Enumerated(EnumType.STRING)
    private CategoryDetails categoryDetails; // 각 카테고리 별로 세부 항목 (없으면 null)
    private String criteria; // 각 카테고리 별 점수, ex) excellent, poor 등
    private String message; // 각 점수 별로 설명 메시지

}
