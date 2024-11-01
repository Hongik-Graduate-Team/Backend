package com.example.Namanba.portfolio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.portfolio.dto.StackDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.service.StackService;
import com.example.Namanba.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stacks")
@Tag(name = "5. [포트폴리오] 기술 스택 API", description = "기술 스택 CRUD API 입니다.")
public class StackController {

    private final StackService stackService;
    private final JwtUtil jwtUtil;
    private final PortfolioRepository portfolioRepository;

    @Operation(summary = "기술 스택을 저장하는 API입니다.")
    @PostMapping
    public SuccessResponse<Void> createStack(@RequestBody List<StackDto> stackDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.createStacks(portfolio, stackDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "기술 스택을 업데이트/수정하는 API입니다.")
    @PutMapping
    public SuccessResponse<Void> updateStack(@RequestBody List<StackDto> stackDtos, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.updateStacks(portfolio, stackDtos);
        return SuccessResponse.empty();
    }

    @Operation(summary = "기술 스택을 삭제하는 API입니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteStacks(@RequestBody List<Long> stackIds, HttpServletRequest httpRequest) {
        User user = jwtUtil.getUserByToken(httpRequest);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        stackService.deleteStacks(portfolio, stackIds);
        return SuccessResponse.empty();
    }
}
