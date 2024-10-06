package com.example.Namanba.Interview.adaptor;

import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.repository.CustomQuestionRepository;
import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.portfolio.exception.PortfolioErrorCode;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CustomQuestionAdaptor {
    private final CustomQuestionRepository customQuestionRepository;

    public CustomQuestion save(CustomQuestion customQuestion) {
        return customQuestionRepository.save(customQuestion);
    }

    public CustomQuestion findByInterview(Interview interview){
        return customQuestionRepository.findByInterview(interview);
    }
}
