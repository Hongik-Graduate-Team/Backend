package com.example.Namanba.Interview.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "custom_question")
public class CustomQuestion {

    @Id
    @Column(name = "customQuestion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customQuestionId;


    private String customQuestions;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

}
