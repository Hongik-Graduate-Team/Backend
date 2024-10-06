package com.example.Namanba.Interview.adaptor;

import com.example.Namanba.Interview.entity.BasicQuestion;
import com.example.Namanba.Interview.exception.InterviewErrorCode;
import com.example.Namanba.Interview.repository.BasicQuestionRepository;
import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.portfolio.entity.subitems.Position;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class BasicQuestionAdaptor {
    private final BasicQuestionRepository basicQuestionRepository;

    public List<BasicQuestion> findByPosition(Position position) {
        List<BasicQuestion> basicQuestions = basicQuestionRepository.findByPosition(position);
        if (basicQuestions.isEmpty()) {
            throw new BaseException(InterviewErrorCode.BASIC_QUESTION_NOT_FOUND);}

        return basicQuestions;
    }
}
