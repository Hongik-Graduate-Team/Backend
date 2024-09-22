package com.example.Namanba.Interview.entity;

import com.example.Namanba.common.entity.BaseTimeEntity;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "interview")
public class Interview extends BaseTimeEntity {

    @Id
    @Column(name = "interview_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    private String interviewTitle;

    private String basicInterview1;

    private String basicInterview2;

    private String basicInterview3;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

}
