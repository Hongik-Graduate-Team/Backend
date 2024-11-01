package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.CertificationDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.CertificationService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certifications")
@Tag(name = "7. [포트폴리오] 자격증 API", description = "자격증 CRUD API 입니다.")
public class CertificationController {

    private final CertificationService certificationService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "자격증을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createCertification(@RequestBody List<CertificationDto> certificationDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        certificationService.createCertifications(portfolio, certificationDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "자격증을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateCertification(@RequestBody List<CertificationDto> certificationDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        certificationService.updateCertifications(portfolio, certificationDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "자격증을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteCertifications(@RequestBody List<Long> certificationIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        certificationService.deleteCertifications(portfolio, certificationIds);
        return SuccessResponse.empty();
    }
}

