package com.example.Namanba.Interview.adaptor;

import com.example.Namanba.Interview.entity.BasicQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.exception.InterviewErrorCode;
import com.example.Namanba.Interview.repository.InterviewRepository;
import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.user.entity.User;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class InterviewAdaptor {
    private final InterviewRepository interviewRepository;

    public Interview save(Interview interview) {
        return interviewRepository.save(interview);
    }

}
