package com.example.Namanba.Interview.service;

import com.example.Namanba.Interview.adaptor.BasicQuestionAdaptor;
import com.example.Namanba.Interview.adaptor.CustomQuestionAdaptor;
import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.converter.InterviewConverter;
import com.example.Namanba.Interview.dto.InterviewDto;
import com.example.Namanba.Interview.entity.BasicQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.service.processor.GenerateCustomQuestionsProcessor;
import com.example.Namanba.portfolio.adaptor.PortfolioAdaptor;
import com.example.Namanba.portfolio.adaptor.PositionAdaptor;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InterviewService {
    private final PositionAdaptor positionAdaptor;
    private final PortfolioAdaptor portfolioAdaptor;
    private final CustomQuestionAdaptor customQuestionAdaptor;
    private final BasicQuestionAdaptor basicQuestionAdaptor;
    private final InterviewAdaptor interviewAdaptor;
    private final GenerateCustomQuestionsProcessor generateCustomQuestionsProcessor;

    public InterviewDto execute(String interviewTitle, User user) {

        Portfolio portfolio = portfolioAdaptor.findByUser(user);
        Interview interview = setupInterviewBase(portfolio, interviewTitle);
        String customQuestions = generateCustomQuestionsProcessor.generateCustomQuestions(portfolio);
        customQuestionAdaptor.save(InterviewConverter.toCustomQuestion(interview, customQuestions));
        return InterviewConverter.toInterviewDto(interview, customQuestions);
    }


    private Interview setupInterviewBase(Portfolio portfolio, String interviewTitle) {
        List<String> basicQuestionList = getBasicQuestionsByRandom(portfolio.getPosition().getPositionName());
        Interview interview = InterviewConverter.toInterview(portfolio, interviewTitle, basicQuestionList);
        interviewAdaptor.save(interview);
        return interview;
    }

    private List<String> getBasicQuestionsByRandom(String positionName) {
        Position position = positionAdaptor.findByPositionName(positionName);
        List<BasicQuestion> basicQuestionList = basicQuestionAdaptor.findByPosition(position);

        Collections.shuffle(basicQuestionList);
        return basicQuestionList.stream()
                .limit(3)
                .map(BasicQuestion::getBasicQuestion)
                .collect(Collectors.toList());
    }
}
