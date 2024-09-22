package com.example.Namanba.Interview.converter;

import com.example.Namanba.Interview.dto.InterviewDto;
import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class InterviewConverter {

    public static Interview toInterview(Portfolio portfolio, String interviewTitle, List<String> basicQuestionList){
        return Interview.builder()
                .interviewTitle(interviewTitle)
                .basicInterview1(basicQuestionList.get(0))
                .basicInterview2(basicQuestionList.get(1))
                .basicInterview3(basicQuestionList.get(2))
                .user(portfolio.getUser())
                .portfolio(portfolio)
                .build();
    }

    public static CustomQuestion toCustomQuestion(Interview interview, String customQuestions){
        return CustomQuestion.builder()
                .interview(interview)
                .customQuestions(customQuestions)
                .build();
    }

    public static InterviewDto toInterviewDto(Interview interview, String customQuestions){
        return InterviewDto.builder()
                .interviewTitle(interview.getInterviewTitle())
                .basicInterview1(interview.getBasicInterview1())
                .basicInterview2(interview.getBasicInterview2())
                .basicInterview3(interview.getBasicInterview3())
                .customQuestions(customQuestions)
                .build();
    }
}
