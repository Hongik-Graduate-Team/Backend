package com.example.Namanba.Interview.usecase;

import com.example.Namanba.Interview.adaptor.CustomQuestionAdaptor;
import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.dto.response.InterviewReportResponse;
import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.entity.Evaluation;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션
public class ReadInterviewReportUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final EvaluationAdaptor evaluationAdaptor;
    private final CustomQuestionAdaptor customQuestionAdaptor;
    private final JwtUtil jwtUtil;

    public InterviewReportResponse execute(Long interviewId,  HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Interview interview = interviewAdaptor.findByInterviewIdAndUser(interviewId, user);
        Evaluation evaluation = evaluationAdaptor.findWithoutLockByInterview(interview);
        CustomQuestion customQuestions = customQuestionAdaptor.findByInterview(interview);
        return InterviewReportResponse.of(interview, evaluation, customQuestions);
    }
}
