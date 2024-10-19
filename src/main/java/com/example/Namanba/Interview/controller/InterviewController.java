package com.example.Namanba.Interview.controller;

import com.example.Namanba.Interview.dto.InterviewDto;
import com.example.Namanba.Interview.service.InterviewService;
import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
@Tag(name = "사용자 질문 API", description = "사용자의 정보에 따른 면접 질문을 반환합니다.")
public class InterviewController {
    private final JwtUtil jwtUtil;
    private final InterviewService interviewService;

    @Operation(summary = "사용자 맞춤 생성형 면접 질문과 사용자가 선택한 직업의 기본 CS 질문이 반환됩니다.")
    @GetMapping
    public SuccessResponse<InterviewDto> getQuestions(@RequestParam String interviewTitle, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        return SuccessResponse.of(interviewService.execute(interviewTitle, user));
    }

    @Operation(summary = "사용자 맞춤 생성형 면접 질문이 반환됩니다.")
    @GetMapping("/custom")
    public SuccessResponse<List<String>> getCustoms(@RequestParam Long interviewId){
        return SuccessResponse.of(interviewService.ShowcustomQuestions(interviewId));
    }

}
