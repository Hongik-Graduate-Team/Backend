package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.StackDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.StackService;
import com.example.Namanba.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stacks")
public class StackController {

    private final StackService stackService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @PostMapping
    public SuccessResponse<Void> createStack(@RequestBody List<StackDto> stackDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.createStacks(portfolio, stackDtos);
        return SuccessResponse.empty();
    }

    @PutMapping
    public SuccessResponse<Void> updateStack(@RequestBody List<StackDto> stackDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.updateStacks(portfolio, stackDtos);
        return SuccessResponse.empty();
    }

    @DeleteMapping
    public SuccessResponse<Void> deleteStacks(@RequestBody List<Long> stackIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.deleteStacks(portfolio, stackIds);
        return SuccessResponse.empty();
    }
}
