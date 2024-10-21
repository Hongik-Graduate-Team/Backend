package com.example.Namanba.Interview.entity;

import com.example.Namanba.portfolio.entity.subitems.Position;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Table(name = "basic_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_question_id")
    private Long basicQuestionId;

    private String basicQuestion;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

}
