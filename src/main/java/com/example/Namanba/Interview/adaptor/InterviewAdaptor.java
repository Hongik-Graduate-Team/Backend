package com.example.Namanba.Interview.adaptor;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.Interview.exception.InterviewErrorCode;
import com.example.Namanba.Interview.repository.InterviewRepository;
import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


@Adaptor
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션
public class InterviewAdaptor {
    private final InterviewRepository interviewRepository;


    public boolean existsByInterview(Long interviewId){
        return interviewRepository.existsByInterviewId(interviewId);
    }
    public Interview findByInterviewId(Long interviewId){
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new BaseException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }

    public Interview findByInterviewIdAndUser(Long interviewId, User user) {
        return interviewRepository.findByInterviewIdAndUser(interviewId, user)
                .orElseThrow(() -> new BaseException(InterviewErrorCode.INTERVIEW_ACCESS_DENIED));
    }

    // User 객체로 인터뷰 목록 조회 (페이징)
    public Page<Interview> findWithCustomQuestionsByUser(User user, PageRequest pageRequest) {
        return interviewRepository.findWithCustomQuestionsByUser(user, pageRequest);
    }

    @Transactional
    public Interview save(Interview interview) {
        return interviewRepository.save(interview);
    }

}
