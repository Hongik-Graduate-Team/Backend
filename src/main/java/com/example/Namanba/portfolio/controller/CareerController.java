package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.CareerDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.CareerService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/careers")
@Tag(name = "4. [포트폴리오] 경력사항 API", description = "경력사항 CRUD API 입니다.")
public class CareerController {

    private final CareerService careerService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "경력사항을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createCareer(@RequestBody List<CareerDto> careerDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        careerService.createCareers(portfolio, careerDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "경력사항을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateCareer(@RequestBody List<CareerDto> careerDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        careerService.updateCareers(portfolio, careerDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "경력사항을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteCareers(@RequestBody List<Long> careerIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        careerService.deleteCareers(portfolio, careerIds);
        return SuccessResponse.empty();
    }
}