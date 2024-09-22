package com.example.Namanba.Interview.controller;

import com.example.Namanba.Interview.dto.InterviewDto;
import com.example.Namanba.Interview.service.InterviewService;
import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
public class InterviewController {

    private final JwtUtil jwtUtil;
    private final InterviewService interviewService;

    @GetMapping
    public SuccessResponse<InterviewDto> getQuestions(@RequestParam String interviewTitle, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        return SuccessResponse.of(interviewService.execute(interviewTitle, user));
    }

}
