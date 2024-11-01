package com.example.Namanba.portfolio.controller;


import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.PortfolioResponseDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.PortfolioService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@Tag(name = "1. 포트폴리오 API", description = "포트폴리오 API 입니다.")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "사용자의 포트폴리오를 조회하는 API입니다.")
    @GetMapping
    public SuccessResponse<PortfolioResponseDto> getPortfolio(HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        return SuccessResponse.of(portfolioService.getPortfolio(user));
    }
    @Operation(summary = "사용자가 지원하는 직군을 입력받는 API입니다.")
    @PutMapping("/position")
    public SuccessResponse<Void> updatePositionName(@RequestParam String positionName, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        portfolioService.updatePositions(portfolio, positionName);
        return SuccessResponse.empty();
    }
}