package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.AwardDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.AwardService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/awards")
@Tag(name = "6. [포트폴리오] 수상 내역 API", description = "수상내역 CRUD API 입니다.")
public class AwardController {

    private final AwardService awardService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "수상 내역을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createAward(@RequestBody List<AwardDto> awardDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.createAwards(portfolio,awardDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "수상 내역을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateAward(@RequestBody List<AwardDto> awardDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.updateAwards(portfolio,awardDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "수상 내역을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteAwards(@RequestBody List<Long> awardIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        awardService.deleteAwards(portfolio,awardIds);
        return SuccessResponse.empty();
    }
}