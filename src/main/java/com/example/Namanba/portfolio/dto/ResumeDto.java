package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResumeDto {
    private Long resumeId;
    private int questionNum;
    private String question;
    private String answer;

}
