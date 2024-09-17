package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.LanguageCertDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.LanguageCertService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/language-certs")
public class LanguageCertController {

    private final LanguageCertService languageCertService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @PostMapping
    public SuccessResponse<Void> createLanguageCert(@RequestBody List<LanguageCertDto> languageCertDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.createLanguageCert(portfolio, languageCertDtos);
        return SuccessResponse.empty();
    }

    @PutMapping
    public SuccessResponse<Void> updateLanguageCert(@RequestBody List<LanguageCertDto> languageCertDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.updateLanguageCert(portfolio, languageCertDtos);
        return SuccessResponse.empty();
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteLanguageCert(@RequestBody List<Long> languageCertsIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        languageCertService.deleteLanguageCert(portfolio, languageCertsIds);
        return SuccessResponse.empty();
    }
}
