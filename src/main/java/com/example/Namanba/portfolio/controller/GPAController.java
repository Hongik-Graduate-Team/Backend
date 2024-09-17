package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.GPADto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.GPAService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpas")
public class GPAController {

    private final GPAService gpaService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @PostMapping
    public SuccessResponse<Void> createGPA(@RequestBody List<GPADto> gpaDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.createGPAs(portfolio, gpaDtos);
        return SuccessResponse.empty();
    }

    @PutMapping
    public SuccessResponse<Void> updateGPA(@RequestBody List<GPADto> gpaDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.updateGPAs(portfolio, gpaDtos);
        return SuccessResponse.empty();
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteGPAs(@RequestBody List<Long> gpaIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        gpaService.deleteGPAs(portfolio, gpaIds);
        return SuccessResponse.empty();
    }
}
