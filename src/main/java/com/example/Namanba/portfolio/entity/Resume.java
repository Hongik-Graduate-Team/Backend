package com.example.Namanba.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @Column(name = "resume_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column(name = "question_num", nullable = false)
    private int questionNum;

    @Column(name = "question", nullable = false)
    private String question;


    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public void updateQuestion(String question) {
        this.question = question;
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }
}
