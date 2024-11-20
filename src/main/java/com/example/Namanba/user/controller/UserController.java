package com.example.Namanba.user.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.user.dto.UserProfileResponse;
import com.example.Namanba.user.service.GetUserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 프로필 API", description = "사용자의 프로필 관련 API 입니다.")
@RequestMapping("/api/user")
public class UserController {
    private final GetUserProfileUseCase getUserProfileUseCase;

    @Operation(summary = "사용자의 프로필을 조회합니다.")
    @GetMapping("/profile")
    public SuccessResponse<UserProfileResponse> getUserProfile(HttpServletRequest httpRequest) {
        return SuccessResponse.of(getUserProfileUseCase.execute(httpRequest));
    }
}
