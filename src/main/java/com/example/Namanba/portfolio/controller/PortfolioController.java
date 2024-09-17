package com.example.Namanba.portfolio.controller;


import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.PortfolioResponseDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.PortfolioService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @GetMapping
    public SuccessResponse<PortfolioResponseDto> getPortfolio(HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        if (portfolioService.getPortfolio(user) == null) {
            return SuccessResponse.empty();
        }
        return SuccessResponse.of(portfolioService.getPortfolio(user));
    }
    @PutMapping("/position")
    public SuccessResponse<Void> updatePositionName(@RequestParam String positionName, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        portfolioService.updatePositions(portfolio, positionName);
        return SuccessResponse.empty();
    }
}