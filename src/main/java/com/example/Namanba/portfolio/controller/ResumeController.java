package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.ResumeDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.ResumeService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @PostMapping
    public SuccessResponse<Void> createResume(@RequestBody List<ResumeDto> resumeDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.createResumes(portfolio, resumeDtos);
        return SuccessResponse.empty();
    }

    @PutMapping
    public SuccessResponse<Void> updateResume(@RequestBody List<ResumeDto> resumeDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.updateResumes(portfolio, resumeDtos);
        return SuccessResponse.empty();
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteResumes(@RequestBody List<Long> resumeIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        resumeService.deleteResumes(portfolio, resumeIds);
        return SuccessResponse.empty();
    }
}

