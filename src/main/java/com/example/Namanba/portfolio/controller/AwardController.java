package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.AwardDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.AwardService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/awards")
public class AwardController {

    private final AwardService awardService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;


    @PostMapping
    public SuccessResponse<List<AwardDto>> createAward(@RequestBody List<AwardDto> awardDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.createAwards(portfolio,awardDtos);
        return SuccessResponse.empty();
    }

    @PutMapping
    public SuccessResponse<AwardDto> updateAward(@RequestBody List<AwardDto> awardDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.updateAwards(portfolio,awardDtos);
        return SuccessResponse.empty();
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteAwards(@RequestBody List<Long> awardIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.deleteAwards(portfolio,awardIds);
        return SuccessResponse.empty();
    }
}