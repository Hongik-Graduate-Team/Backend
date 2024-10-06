package com.example.Namanba.Interview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@AllArgsConstructor
@Table(name = "custom_question")
public class CustomQuestion {

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
