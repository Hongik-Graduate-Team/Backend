package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.LanguageCertDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.LanguageCertService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/language-certs")
@Tag(name = "8. [포트폴리오] 어학 자격증 API", description = "어학 자격증 CRUD API 입니다.")
public class LanguageCertController {

    private final LanguageCertService languageCertService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "어학 자격증을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createLanguageCert(@RequestBody List<LanguageCertDto> languageCertDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.createLanguageCert(portfolio, languageCertDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "어학 자격증을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateLanguageCert(@RequestBody List<LanguageCertDto> languageCertDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.updateLanguageCert(portfolio, languageCertDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "어학 자격증을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteLanguageCert(@RequestBody List<Long> languageCertsIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.deleteLanguageCert(portfolio, languageCertsIds);
        return SuccessResponse.empty();
    }
}
