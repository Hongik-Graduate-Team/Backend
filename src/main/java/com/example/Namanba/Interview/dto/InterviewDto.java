package com.example.Namanba.Interview.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterviewDto {
    private Long interviewId;
    private String interviewTitle;
    private String basicInterview1;
    private String basicInterview2;
    private String basicInterview3;
    private List<String> customQuestions;
}
