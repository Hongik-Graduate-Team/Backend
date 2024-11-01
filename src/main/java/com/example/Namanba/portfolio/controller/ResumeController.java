package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.ResumeDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.ResumeService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
@Tag(name = "9. [포트폴리오] 자기소개서 API", description = "자기소개서 CRUD API 입니다.")
public class ResumeController {

    private final ResumeService resumeService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "자기소개서를 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createResume(@RequestBody List<ResumeDto> resumeDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.createResumes(portfolio, resumeDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "자기소개서를 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateResume(@RequestBody List<ResumeDto> resumeDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.updateResumes(portfolio, resumeDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "자기소개서를 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteResumes(@RequestBody List<Long> resumeIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.deleteResumes(portfolio, resumeIds);
        return SuccessResponse.empty();
    }
}

