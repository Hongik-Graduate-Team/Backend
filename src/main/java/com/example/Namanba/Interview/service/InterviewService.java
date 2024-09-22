package com.example.Namanba.Interview.service;

import com.example.Namanba.Interview.adaptor.BasicQuestionAdaptor;
import com.example.Namanba.Interview.adaptor.CustomQuestionAdaptor;
import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.converter.InterviewConverter;
import com.example.Namanba.Interview.dto.InterviewDto;
import com.example.Namanba.Interview.entity.BasicQuestion;
import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.service.processor.GenerateCustomQuestionsProcessor;
import com.example.Namanba.portfolio.adaptor.PortfolioAdaptor;
import com.example.Namanba.portfolio.adaptor.PositionAdaptor;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<String> customQuestionList = extractQuestions(customQuestions);

        return InterviewConverter.toInterviewDto(interview, customQuestionList);
    }

    public List<String> ShowcustomQuestions(Long interviewId){
        CustomQuestion customQuestion = customQuestionAdaptor.findByInterviewId(interviewId);
        List<String> questions = extractQuestions(customQuestion.getCustomQuestions());
        return questions;
    }

    private List<String> extractQuestions(String message) {
        List<String> questions = new ArrayList<>();

        // 정규식을 이용해 숫자를 기준으로 질문을 나눔
        String[] splitQuestions = message.split("\\d+\\.\\s*");

        // 첫 번째 질문이 없을 수 있으므로 빈 문자열이 아닌 경우만 처리
        for (String question : splitQuestions) {
            if (!question.trim().isEmpty()) {
                questions.add(question.trim()); // 공백을 제거하고 리스트에 추가
            }
        }

        return questions;
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
