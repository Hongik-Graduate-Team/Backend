package com.example.Namanba.Interview.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.dto.request.InterviewPageRequest;
import com.example.Namanba.Interview.dto.response.InterviewListResponse;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@UseCase
@RequiredArgsConstructor
public class ReadInterviewListUseCase {
    private final InterviewAdaptor interviewAdaptor;
    private final JwtUtil jwtUtil;

    public Page<InterviewListResponse> execute(InterviewPageRequest pageRequest,  HttpServletRequest httpRequest){
        User user = jwtUtil.getUserByToken(httpRequest);
        return interviewAdaptor.findWithCustomQuestionsByUser(user,pageRequest.of()).map(InterviewListResponse::of);
    }
}
