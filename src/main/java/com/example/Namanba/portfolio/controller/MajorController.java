package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.MajorDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.MajorService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/majors")
@Tag(name = "2. [포트폴리오] 전공 API", description = "전공 CRUD API 입니다.")
public class MajorController {

    private final MajorService majorService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;
    @Operation(summary = "전공을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createMajor(@RequestBody List<MajorDto> majorDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        majorService.createMajors(portfolio, majorDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "전공을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateMajor(@RequestBody List<MajorDto> majorDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        majorService.updateMajors(portfolio, majorDtos);
        return SuccessResponse.empty();
    }
    @Operation(summary = "전공을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteMajors(@RequestBody List<Long> majorIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        majorService.deleteMajors(portfolio, majorIds);
        return SuccessResponse.empty();
    }
}
