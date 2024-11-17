package com.example.Namanba.Interview.entity;

import com.example.Namanba.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@AllArgsConstructor
@Table(name = "custom_question")
public class CustomQuestion extends BaseTimeEntity {

    @Id
    @Column(name = "customQuestion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customQuestionId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String customQuestions;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

}
