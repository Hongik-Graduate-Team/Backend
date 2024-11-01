package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.GPADto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.GPAService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpas")
@Tag(name = "3. [포트폴리오] 학점 API", description = "학점 CRUD API 입니다.")
public class GPAController {

    private final GPAService gpaService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "학점을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createGPA(@RequestBody List<GPADto> gpaDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.createGPAs(portfolio, gpaDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "학점을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateGPA(@RequestBody List<GPADto> gpaDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.updateGPAs(portfolio, gpaDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "학점을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteGPAs(@RequestBody List<Long> gpaIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.deleteGPAs(portfolio, gpaIds);
        return SuccessResponse.empty();
    }
}
